package com.github.attiand.archive;

import java.net.URI;

public class FeedSourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FeedSourceException(URI uri, Throwable e) {
		super("Can't read from: " + uri, e);
	}

	public FeedSourceException(String message, Throwable t) {
		super(message, t);
	}
}
