package com.github.attiand.feedarchive;

import java.net.URI;

public interface FeedSourceFactory {

	FeedSource create(URI uri);

}
