package com.github.attiand.archive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import com.github.attiand.archive.internal.AbstractFeedSource;

public class FeedSourceFactory {

	enum SSL {
		INSECURE, PROPERTY
	}

	private final SSL ssl;

	private FeedSourceFactory(SSL ssl) {
		this.ssl = ssl;
	}

	public static FeedSourceFactory insecure() {
		return new FeedSourceFactory(SSL.INSECURE);
	}

	public static FeedSourceFactory secure() {
		return new FeedSourceFactory(SSL.PROPERTY);
	}

	public AbstractFeedSource create(String schema) {
		if (schema == null) {
			return new FileClient();
		} else if (schema.equals("https")) {
			if (ssl == SSL.INSECURE) {
				return new TrustedHttpsClient();
			} else {
				return new HttpsClient();
			}
		} else {
			return new DefaultClient();
		}
	}

	class FileClient implements AbstractFeedSource {

		@Override
		public InputStream getContent(URI uri) {
			try {
				return Files.newInputStream(Paths.get(uri.toString()));
			} catch (IOException e) {
				throw new FeedSourceException(uri, e);
			}
		}

		@Override
		public void close() {
		}
	}

	class DefaultClient implements AbstractFeedSource {

		@Override
		public InputStream getContent(URI uri) {
			try {
				return uri.toURL().openStream();
			} catch (IOException e) {
				throw new FeedSourceException(uri, e);
			}
		}

		@Override
		public void close() {
		}
	}

	static class AbstractHttpsClient implements AbstractFeedSource {
		private final CloseableHttpClient client;

		AbstractHttpsClient(CloseableHttpClient client) {
			this.client = client;
		}

		@Override
		public InputStream getContent(URI uri) {
			HttpGet request = new HttpGet(uri);

			try {
				CloseableHttpResponse response = client.execute(request);
				return response.getEntity().getContent();
			} catch (IOException e) {
				throw new FeedSourceException(uri, e);
			}
		}

		@Override
		public void close() {
			try {
				client.close();
			} catch (IOException e) {
				throw new FeedSourceException("Can't close https connection", e);
			}
		}
	}

	static class HttpsClient extends AbstractHttpsClient {

		HttpsClient() {
			super(HttpClientBuilder.create().useSystemProperties().build());
		}
	}

	static class TrustedHttpsClient extends AbstractHttpsClient {

		public TrustedHttpsClient() {
			super(createClient());
		}

		static CloseableHttpClient createClient() {
			try {
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
				HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
				return clientBuilder.build();
			} catch (Exception e) {
				throw new FeedSourceException("Can't create Https factory", e);
			}
		}
	}
}
