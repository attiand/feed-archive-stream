package com.github.attiand.archive;

import java.net.URI;

public class FeedSource {

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
		return fromUri(uri, FeedSourceFactory.secure());
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
