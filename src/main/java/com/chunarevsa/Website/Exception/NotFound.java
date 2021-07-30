package com.chunarevsa.Website.Exception;

import java.util.NoSuchElementException;

public class NotFound extends NoSuchElementException {

	private boolean i;

	public boolean getI() {
		return this.i;
	}

	public void setI(boolean i) {
		this.i = i;
	}

	public NotFound(boolean i) {
		this.i = i;
	}

	public NotFound() {
	}

}
