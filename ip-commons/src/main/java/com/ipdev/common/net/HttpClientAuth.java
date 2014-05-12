package com.ipdev.common.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientAuth {
	/**
	 * Do a HTTP GET request and return the result.
	 * 
	 * @param uri
	 *            URI for HTTP GET request
	 * @param headerAccept
	 *            Empty or header accept
	 * @param headerAuthorization
	 *            Empty or authorization header
	 * @return Response (content of the received entity)
	 * @throws RuntimeException
	 *             When status code is not HTTP_OK
	 */
	public static String httpGet(String uri, String headerAccept,
			String headerAuthorization) throws RuntimeException {
		StringBuilder result = new StringBuilder();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = null;
		try {
			getRequest = new HttpGet(uri);
			if (!headerAccept.isEmpty()) {
				getRequest.addHeader(HttpHeaders.ACCEPT, headerAccept);
			}
			if (!headerAuthorization.isEmpty()) {
				getRequest.addHeader(HttpHeaders.AUTHORIZATION,
						headerAuthorization);
			}
			HttpResponse response = httpClient.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("ERROR: HTTP code: " + statusCode);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();

		} catch (ConnectException e) {
			System.err.println("ConnectException: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		//	httpClient.getConnectionManager().shutdown();
			if (getRequest!=null) getRequest.releaseConnection();
		}
		return result.toString();
	}

	/**
	 * Do a HTTT POST request and return the status code.
	 * 
	 * @param uri
	 *            URI for HTTP POST request
	 * @param headerAuthorization
	 *            Empty or authorization header
	 * @param stringEntity
	 *            String entity which will be posted
	 * @param entityConentType
	 *            Empty or entity content type
	 * @return True, when request was successful
	 * @throws RuntimeException
	 *             When status code is not HTTP_CREATED
	 */
	public static boolean httpPost(String uri, String headerAuthorization,
			String stringEntity, ContentType entityConentType)
			throws RuntimeException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = null;
		try {
			postRequest = new HttpPost(uri);
			if (!headerAuthorization.isEmpty()) {
				postRequest.addHeader(HttpHeaders.AUTHORIZATION,
						headerAuthorization);
			}
			StringEntity ent = new StringEntity(stringEntity);
			if (entityConentType != null) {
				ent.setContentType(entityConentType.toString());
			}
			postRequest.setEntity(ent);
			HttpResponse response = httpClient.execute(postRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("ERROR: HTTP code: " + statusCode);
			}
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		//	httpClient.getConnectionManager().shutdown();
			if (postRequest!=null) postRequest.releaseConnection();
		}
		return false;
	}
}
