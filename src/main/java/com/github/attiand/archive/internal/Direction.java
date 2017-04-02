package com.github.attiand.archive.internal;

public enum Direction {
	FORWARD("next-archive"), BACKWARD("prev-archive");

	private final String rel;

	private Direction(String rel) {
		this.rel = rel;
	}

	public String getRel() {
		return rel;
	}
}
