package com.github.attiand.feedarchive;

import java.io.InputStream;

public interface FeedSource extends AutoCloseable {

	InputStream getContent();

	@Override
	void close();
}
