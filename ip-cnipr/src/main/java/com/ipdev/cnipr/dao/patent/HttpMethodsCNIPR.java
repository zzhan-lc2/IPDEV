package com.ipdev.cnipr.dao.patent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ipdev.common.net.HttpClientUtility;
import com.ipdev.common.net.HttpMethodsInterface;

@SuppressWarnings("deprecation")
public class HttpMethodsCNIPR implements HttpMethodsInterface {
    static final Log LOG = LogFactory.getLog(HttpMethodsCNIPR.class);
    static final String DEFAULT_CHARSET = "UTF-8";

    static HttpClient currentActiveClient = null; // TODO: support concurrent later

    final boolean keepConnection;

    public HttpMethodsCNIPR(boolean keepConnection) {
        this.keepConnection = keepConnection;
    }

    public void close() {
        if (currentActiveClient != null) {
            currentActiveClient.getConnectionManager().shutdown();
            currentActiveClient = null;
        }
    }

    HttpClient getHttpClient() {
        if (currentActiveClient == null) {
            HttpClient httpclient = new DefaultHttpClient();
            httpclient = HttpClientUtility.wrapHttpClient(httpclient);
            currentActiveClient = httpclient;
        }
        return currentActiveClient;
    }

    void releaseHttpClient(HttpClient client) {
        if (!keepConnection) {
            client.getConnectionManager().shutdown();
            if (currentActiveClient == client) {
                currentActiveClient = null;
            }
            client = null;
        }
    }

    @Override
    public String post(String url, List<NameValuePair> nvps) {
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("ContentType", "application/x-www-form-urlencoded");
        String ss = null;
        try {
            // 设置表单提交编码为UTF-8
            UrlEncodedFormEntity entry = new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET);
            entry.setContentType("application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET);
            httppost.setEntity(entry);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            ss = EntityUtils.toString(entity, DEFAULT_CHARSET);

            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            releaseHttpClient(httpclient);
            // httpclient.getConnectionManager().shutdown();
        }
        return ss;
    }

    @Override
    public String get(String url, List<NameValuePair> nvps) {
        HttpClient httpclient = getHttpClient();

        HttpGet httpget = new HttpGet(url);
        String ss = null;
        try {
            String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            HttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            ss = EntityUtils.toString(entity, DEFAULT_CHARSET);

            EntityUtils.consume(entity);
        } catch (Exception e) {
            LOG.error("caught exception: " + e);
        } finally {
            releaseHttpClient(httpclient);
        }
        return ss;
    }
}
