package com.github.attiand.archive;

import java.net.URI;
import java.net.URISyntaxException;

import com.github.attiand.archive.internal.Direction;
import com.github.attiand.archive.internal.FeedSourceFactory;

public class FeedSource {

	public static Feed first(String uri) throws URISyntaxException {
		return first(uri, FeedSourceFactory.secure());
	}

	public static Feed first(String uri, FeedSourceFactory factory) throws URISyntaxException {
		return new Feed(factory, Direction.FORWARD, new URI(uri));
	}

	public static Feed last(String uri) throws URISyntaxException {
		return last(uri, FeedSourceFactory.secure());
	}

	public static Feed last(String uri, FeedSourceFactory factory) throws URISyntaxException {
		return new Feed(factory, Direction.BACKWARD, new URI(uri));
	}
}
