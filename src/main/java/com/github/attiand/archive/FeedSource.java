package com.github.attiand.archive;

import java.net.URI;
import java.net.URISyntaxException;

import com.github.attiand.archive.internal.Direction;
import com.github.attiand.archive.internal.FeedSourceFactory;

public class FeedSource {

	public static Feed fromUri(String uri) throws URISyntaxException {
		return first(uri, FeedSourceFactory.secure());
	}

	public static Feed first(String uri, FeedSourceFactory factory) throws URISyntaxException {
		return new Feed(factory, Direction.FORWARD, new URI(uri));
	}
}
