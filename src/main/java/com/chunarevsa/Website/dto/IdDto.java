package com.chunarevsa.Website.dto;

public class IdDto {
	
	private long idByJson;

	public IdDto() {}

	public IdDto(long idByJson) {
		this.idByJson = idByJson;
	}


	public long getIdByJson() {
		return this.idByJson;
	}

	public void setIdByJson(long idByJson) {
		this.idByJson = idByJson;
	}

	@Override
	public String toString() {
		return "{" +
			" idByJson='" + getIdByJson() + "'" +
			"}";
	}
	

}