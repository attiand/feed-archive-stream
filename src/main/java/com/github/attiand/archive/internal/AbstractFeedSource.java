package com.github.attiand.archive.internal;

import java.io.InputStream;
import java.net.URI;

public interface AbstractFeedSource extends AutoCloseable {

	InputStream getContent(URI uri);

	@Override
	void close();
}
