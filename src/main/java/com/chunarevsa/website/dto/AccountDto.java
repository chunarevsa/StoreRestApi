package com.chunarevsa.website.dto;

import com.chunarevsa.website.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

	private String amount;
	private String currencyTitle;

	public AccountDto() {
	}

	public static AccountDto fromUser (Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.setAmount(account.getAmount());
		accountDto.setCurrencyTitle(account.getCurrencyTitle());
		return accountDto;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyTitle() {
		return this.currencyTitle;
	}

	public void setCurrencyTitle(String currencyTitle) {
		this.currencyTitle = currencyTitle;
	}

	@Override
	public String toString() {
		return "{" +
			" amount='" + getAmount() + "'" +
			", currencyTitle='" + getCurrencyTitle() + "'" +
			"}";
	}

}
