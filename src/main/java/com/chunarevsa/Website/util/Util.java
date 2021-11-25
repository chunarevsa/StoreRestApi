package com.chunarevsa.Website.util;

import java.util.UUID;

public class Util {

	private Util () { // TODO: ?
		throw new UnsupportedOperationException("Cannot instantiate a Util class");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}
	
}
