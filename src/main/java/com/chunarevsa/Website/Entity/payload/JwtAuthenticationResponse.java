package com.chunarevsa.Website.Entity.payload;

public class JwtAuthenticationResponse {
	
	private String accessToken;
   private String refreshToken;
   private String tokenType;
   private Long expiryDuration;


	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = "Bearer ";
		this.expiryDuration = expiryDuration;
	}


	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getExpiryDuration() {
		return this.expiryDuration;
	}

	public void setExpiryDuration(Long expiryDuration) {
		this.expiryDuration = expiryDuration;
	}


}
