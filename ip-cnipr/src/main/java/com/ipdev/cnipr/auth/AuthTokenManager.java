package com.ipdev.cnipr.auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import com.ipdev.common.IpdException;
import com.ipdev.common.auth.AuthToken;
import com.ipdev.common.net.HttpClientUtility;
import com.ipdev.common.net.UserAgents;
import com.ipdev.common.utility.json.GsonJsonHelper;
import com.ipdev.common.utility.json.JsonHelper;

public class AuthTokenManager {
	private static final Log LOG = LogFactory.getLog(AuthTokenManager.class);
	
	static final String DEFAULT_CHARSET = "UTF-8";
	
	static String ACCOUNT_NAME = "yuyangip";
	static String ACCOUNT_PASSWORD = "kris526216107";
	
	String userAgent = UserAgents.getRandomAgent();
	
	String authUrl = OAuth2ClientCredentials.AUTH_URL; 
	
	String localDirForTokenCache = System.getProperty("java.io.tmpdir");
	String tokenLocalDile;
	JsonHelper jsonHelper;
	boolean refreshOnExpire = true;
	AuthToken authToken = new AuthToken();
	
	public AuthTokenManager() {
		String dateFormat = "EEE, dd MMM yyyy HH:mm:ss zzz";
		jsonHelper = new GsonJsonHelper()	// default Json helper
			.setDateFormat(dateFormat)
			.setSerializeNulls(true);
		
		setLocalDirForTokenCahce(localDirForTokenCache);
	}
	
	public void setLocalDirForTokenCahce(String tokenCacheDir) {
		localDirForTokenCache = tokenCacheDir;
		tokenLocalDile = localDirForTokenCache + "/" + ACCOUNT_NAME + "-cnipr-token.json";
	}
	
	public void setRefreshOnExpire(boolean refreshOnExpire) {
		this.refreshOnExpire = refreshOnExpire;
	}
	
	public boolean getRefreshOnExpire() {
		return this.refreshOnExpire;
	}
	
	// sample: "https://59.151.93.239/oauth2/access_token?client_id=0F88A0C6E150D4310BA3C9CB61F81929&client_secret=7DF87E7E3AD02A06E950D4ED4E19CE70&redirect_uri=http://221.122.40.156/portal-ps/user/redirect&grant_type=authorization_code&code=534688191c32602a0695f5063f51fc4f";
	// Sample: https://open.cnipr.com/oauth2/authorize?client_id=clientId&response_type=token&redirect_uri=noReUri

	String constructGetUrl(String responseType, String redirectUri) {
		String params = new StringBuilder()
			.append("client_id=").append(OAuth2ClientCredentials.API_KEY).append("&")
			.append("response_type=").append(responseType).append("&")
			.append("state=").append("test").append("&")
			.append("redirect_uri=").append(redirectUri)
			.toString();
		return authUrl + "?" + (params);
	}
	
	public String getAccessCode() {
		String code = null;
		
		String url = this.constructGetUrl("code", OAuth2ClientCredentials.REGISTERED_URL);
		String body = getHttpResponseBody(url);
	    // TODO: get "code" out of the body
		code = body;
		
		return code;
	}
	
	public synchronized final AuthToken getAuthToken() {
		if (StringUtils.isEmpty(authToken.getAccessToken())) {
			AuthToken tmpToken = readTokenFromLocalFile();
			if (tmpToken!=null) {
				authToken = tmpToken;
			}
		}
		
		Date now = new Date();
		if (StringUtils.isNotEmpty(authToken.getAccessToken())) {
			if (now.before(authToken.getTokenExpireTime())) {
				return authToken;
			}
		}
		
		// TODO: either request or refresh the token
		authToken = requestAccessToken();
		this.storeTokenToLocalFile(authToken);
		
		return authToken;
	}
	
	static final String AUTH_SUCCESS_STRING="检测到已登录的帐号";
	AuthToken requestAccessToken() {
		String url = this.constructGetUrl("token", "noReUri");
		
		String body = null;
		HttpClient httpclient = HttpClientUtility.getHttpClientSSL(); //new DefaultHttpClient();
		
		// Step 1: send in request and expect CNIPR return a login page
		HttpGet httpget = new HttpGet(url);
		try { 
			HttpResponse response = httpclient.execute(httpget);
			
		    LOG.info("HTTP status = " + response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    
		    body = EntityUtils.toString(entity, DEFAULT_CHARSET);
		    if (LOG.isDebugEnabled()) {
		    	LOG.debug("Response body = " + body);
		    }
		    
		    EntityUtils.consumeQuietly(entity);
		    
		} catch (ClientProtocolException e) {
			LOG.error("caught ClientProtocolException:", e);
			throw new IpdException("caught ClientProtocolException for URL:" + url, e);
		} catch (IOException e) {
			LOG.error("caught IOException:", e);
			throw new IpdException("caught IOException for URL:" + url, e);
		} finally {
			httpget.releaseConnection();
		}
		
		if (StringUtils.isEmpty(body)) {
			throw new IpdException("failed to get HTML body from CNIPR URL:" + url);
		}
		if (!StringUtils.contains(body, AUTH_SUCCESS_STRING)) {
			throw new IpdException("failed to get valid response from CNIPR URL:" + url);
		}
		
		// Step 2: post user name and password
		try {
			HttpUriRequest login = RequestBuilder.post()
			        .setUri(new URI(authUrl))
			        .addParameter("account", ACCOUNT_NAME)
			        .addParameter("password", ACCOUNT_PASSWORD)
			        .build();
			
			HttpResponse response = httpclient.execute(login);
			LOG.info("HTTP status = " + response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    
		    body = EntityUtils.toString(entity, DEFAULT_CHARSET);
		    if (LOG.isDebugEnabled()) {
		    	LOG.debug("Response body = " + body);
		    }
		} catch (URISyntaxException e) {
			throw new IpdException("caught URISyntaxException for auth URL:" + authUrl, e);
		} catch (ClientProtocolException e) {
			LOG.error("caught ClientProtocolException:", e);
			throw new IpdException("caught ClientProtocolException for login page", e);
		} catch (IOException e) {
			LOG.error("caught IOException:", e);
			throw new IpdException("caught IOException for login page", e);
		}
		
		/**
		 * A valid response looks like:
		 * <body>
			<div id="openid">ab6f1509925e39836a32f7dd22224a15</div>
			<div id="openkey">8896dbdd8a9323c1e78a9d8c87f5f4c8</div>
			<div id="expires_in">2592000</div>
			<div id="access_token">d9eea4e8ad1119ed30b4e8134aa37b47</div>
			<div id="state">test</div>
  		   </body>
		 */
		return parseTokenFromResponseBody(body, authToken);
	}
	
	public synchronized AuthToken refreshAccessToken(AuthToken authToken) {
		// Temp code: just re-get new access token
		authToken = requestAccessToken();
		// TODO
		
		return authToken;
	}
	
	synchronized void storeTokenToLocalFile(AuthToken authToken) {
		if (null==authToken) return;
		
		Path outputPath = Paths.get(tokenLocalDile);
		
		try (BufferedWriter output = Files.newBufferedWriter(
				outputPath, 
				Charset.forName(DEFAULT_CHARSET), 
				StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

			String jsonOut = jsonHelper.toJsonString(authToken);
			output.write(jsonOut);
			output.flush();
		} catch (IOException e) {
			LOG.error("Caught IOException: ", e);
		}
	}
	
	synchronized AuthToken readTokenFromLocalFile() {
		if (StringUtils.isEmpty(tokenLocalDile)) {
			return null;
		}
		
		Path outputPath = Paths.get(tokenLocalDile);
		try (BufferedReader input = Files.newBufferedReader(
				outputPath, 
				Charset.forName(DEFAULT_CHARSET))) {
			AuthToken token = jsonHelper.fromJsonString(input, AuthToken.class);
			return token;
		} catch (Exception e) {
			LOG.error("Caught Exception: ", e);
			return null;
		}
		
	}
	
	static AuthToken parseTokenFromResponseBody(String response, AuthToken authToken) {
		if (authToken==null) authToken = new AuthToken();
		Date now = new Date();
		String body= StringUtils.substringBetween(response, "<body>", "</body>");
		String[] values = StringUtils.substringsBetween(body, "<div id=\"", "</div>");
		for (String value : values) {
			String[] pair = StringUtils.split(value, "\">");
			String id = StringUtils.lowerCase(StringUtils.trim(pair[0]));
			String v = StringUtils.trim(pair[1]);
			if (StringUtils.equalsIgnoreCase(id, "openid")) {
				authToken.setOpenId(v);
			} else if (StringUtils.equalsIgnoreCase(id, "openkey")) {
				authToken.setOpenKey(v);
			} else if (StringUtils.equalsIgnoreCase(id, "expires_in")) {
				try {
					long timet = Long.parseLong(v) * 1000;
					authToken.setTokenExpireTime(new Date(now.getTime()+timet));
				} catch (NumberFormatException e) {
					throw new IpdException("Wrong format for long integer : " + v, e);
				}
			} else if (StringUtils.equalsIgnoreCase(id, "access_token")) {
				authToken.setAccessToken(v);
			} else if (StringUtils.equalsIgnoreCase(id, "state")) {
				// TODO
			}
		}
		authToken.setTokenAcquireTime(now);
		return authToken;
	}
	
	String getHttpResponseBody(String url) {
		HttpClient httpclient = HttpClientUtility.getHttpClientSSL();
		
		HttpGet httpget = new HttpGet(url);
	//	httpget.setHeader("User-Agent", userAgent);

		try { 
			HttpResponse response = httpclient.execute(httpget);
			
		    LOG.info("HTTP status = " + response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    
		    String body = EntityUtils.toString(entity, DEFAULT_CHARSET);
		    if (LOG.isDebugEnabled()) {
		    	LOG.debug("Response body = " + body);
		    }
		    
		    EntityUtils.consume(entity);

		    return body;
		} catch (ClientProtocolException e) {
			LOG.error("caught ClientProtocolException:", e);
			return null;
		} catch (IOException e) {
			LOG.error("caught IOException:", e);
			return null;
		} finally {
			httpget.releaseConnection();
		}
	}
	
}
