package com.chunarevsa.website1.util;

import java.util.UUID;

public class Util {

	private Util () { 
		throw new UnsupportedOperationException("Ошибка создание класса Util");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}
	
}
