package com.chunarevsa.Website.Entity;

public class Mail {

	 private String from;
    private String to;
    private String subject;
    private String message;

	public Mail() {
	}

	public Mail(String from, String to, String subject, String message) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
	}
	 

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
