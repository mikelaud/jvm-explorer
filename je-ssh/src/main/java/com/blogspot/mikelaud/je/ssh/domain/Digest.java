package com.blogspot.mikelaud.je.ssh.domain;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Objects;
import java.util.stream.IntStream;

public class Digest {

	private final String ALGORITHM;
	private final String BYTES;

	private String toHex(MessageDigest aMessageDigest) {
		byte[] bytes = aMessageDigest.digest();
		return IntStream.range(0, bytes.length).collect
			(	StringBuilder::new
			,	(sb, i)->new Formatter(sb).format("%02x", bytes[i] & 0xff)
			,	StringBuilder::append
			).toString();
	}

	public Digest() {
		ALGORITHM = "";
		BYTES = "";
	}

	public Digest(MessageDigest aMessageDigest) {
		Objects.requireNonNull(aMessageDigest);
		ALGORITHM = aMessageDigest.getAlgorithm();
		BYTES = toHex(aMessageDigest);
	}

	public String getAlgorithm() { return ALGORITHM; }
	public String getBytes() { return BYTES; }

	@Override
	public String toString() {
		return String.format("%s: %s", ALGORITHM, BYTES);
	}

}
