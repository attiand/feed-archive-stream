package com.github.attiand.feedarchive;

import java.net.URI;

import com.github.attiand.feedarchive.internal.DefaultFeedSourceFactory;

public class FeedReader {

	public static FeedSourceFactory unsecure() {
		return DefaultFeedSourceFactory.unsecure();
	}

	public static FeedSourceFactory secure() {
		return DefaultFeedSourceFactory.secure();
	}

	/**
	 * Create feed form uri
	 * 
	 * @param uri
	 *            The uri to read.
	 * @return A new Feed instance.
	 */

	public static Feed fromUri(String uri) {
		return fromUri(URI.create(uri));
	}

	/**
	 * Create feed form uri
	 * 
	 * @param uri
	 *            The uri to read.
	 * @return A new Feed instance.
	 */

	public static Feed fromUri(URI uri) {
		return fromUri(uri, secure());
	}

	/**
	 * Create feed form uri with specified source factory
	 * 
	 * @param uri
	 *            The uri to read.
	 * @param factory
	 *            Factory to provide feeds.
	 * @return A new Feed instance.
	 */

	public static Feed fromUri(String uri, FeedSourceFactory factory) {
		return fromUri(URI.create(uri), factory);
	}

	/**
	 * Create feed form uri with specified source factory
	 * 
	 * @param uri
	 *            The uri to read.
	 * @param factory
	 *            Factory to provide feeds.
	 * @return A new Feed instance.
	 */

	public static Feed fromUri(URI uri, FeedSourceFactory factory) {
		return new Feed(factory, uri);
	}
}
