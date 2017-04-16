package com.github.attiand.archive;

import java.net.URI;

public class FeedSource {

	public static Feed fromUri(String uri) {
		return fromUri(URI.create(uri));
	}

	public static Feed fromUri(URI uri) {
		return fromUri(uri, FeedSourceFactory.secure());
	}

	public static Feed fromUri(String uri, FeedSourceFactory factory) {
		return fromUri(URI.create(uri), factory);
	}

	public static Feed fromUri(URI uri, FeedSourceFactory factory) {
		return new Feed(factory, uri);
	}
}
