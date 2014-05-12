package com.ipdev.common.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class MySSLSocketFactory extends SSLSocketFactory {

	SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(KeyStore truststore) throws 
    	NoSuchAlgorithmException, 
    	KeyManagementException, 
    	KeyStoreException, 
    	UnrecoverableKeyException {
        super(truststore);

        X509TrustManager tm = new X509ExtendedTrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1,
					Socket arg2) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1,
					SSLEngine arg2) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1,
					Socket arg2) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1,
					SSLEngine arg2) throws CertificateException {
				// TODO Auto-generated method stub
				
			}
        };

        sslContext.init(null, new TrustManager[] { tm }, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }	

}
