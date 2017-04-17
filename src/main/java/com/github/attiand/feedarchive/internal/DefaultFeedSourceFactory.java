package com.github.attiand.feedarchive.internal;

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

import com.github.attiand.feedarchive.FeedSource;
import com.github.attiand.feedarchive.FeedSourceException;
import com.github.attiand.feedarchive.FeedSourceFactory;

public class DefaultFeedSourceFactory implements FeedSourceFactory {

	enum SSL {
		UNSECURE, PROPERTY
	}

	private final SSL ssl;

	private DefaultFeedSourceFactory(SSL ssl) {
		this.ssl = ssl;
	}

	public static DefaultFeedSourceFactory unsecure() {
		return new DefaultFeedSourceFactory(SSL.UNSECURE);
	}

	public static DefaultFeedSourceFactory secure() {
		return new DefaultFeedSourceFactory(SSL.PROPERTY);
	}

	@Override
	public FeedSource create(URI uri) {
		String schema = uri.getScheme();

		if (schema == null) {
			return new FileSource(uri);
		} else if (schema.equals("https")) {
			if (ssl == SSL.UNSECURE) {
				return new TrustedHttpsSource(uri);
			} else {
				return new HttpsSource(uri);
			}
		} else {
			return new DefaultSource(uri);
		}
	}

	class FileSource implements FeedSource {

		private final URI uri;

		public FileSource(URI uri) {
			this.uri = uri;
		}

		@Override
		public InputStream getContent() {
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

	class DefaultSource implements FeedSource {

		private final URI uri;
		
		public DefaultSource(URI uri) {
			this.uri = uri;
		}

		@Override
		public InputStream getContent() {
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

	static class AbstractHttpsSource implements FeedSource {
		
		private final URI uri;
		private final CloseableHttpClient client;

		AbstractHttpsSource(URI uri, CloseableHttpClient client) {
			this.uri = uri;
			this.client = client;
		}

		@Override
		public InputStream getContent() {
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

	static class HttpsSource extends AbstractHttpsSource {

		HttpsSource(URI uri) {
			super(uri, HttpClientBuilder.create().useSystemProperties().build());
		}
	}

	static class TrustedHttpsSource extends AbstractHttpsSource {

		public TrustedHttpsSource(URI uri) {
			super(uri, createClient());
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
