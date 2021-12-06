package com.chunarevsa.Website.service;

import java.util.Set;

import com.chunarevsa.Website.Entity.Account;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.repo.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Set<Account> byeCurrency(String currencyTitle, String amountDomesticCurrency, User user) {
	
		if (!validateAmount(amountDomesticCurrency)) {
			System.err.println("!validateAmount(amountDomesticCurrency)"); // TODO: искл
		}
		Set<Account> userAccounts = user.getAccounts();
		Account userAccount = userAccounts.stream()
				.filter(acc -> currencyTitle.equals(acc.getCurrencyTitle()))
				.findAny().orElse(null);
		
		if (userAccount == null) {
			System.err.println("userAccount == null");
			Account newAccount = new Account();
			//newAccount.setAmount(String.valueOf(1));
			newAccount.setCurrencyTitle(currencyTitle);
			Account savedAccount = accountRepository.save(newAccount);
			userAccounts.add(savedAccount);
			return userAccounts; // TODO: Проверить нужно ли
		}

		int userBalanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
		int add = Integer.parseInt(amountDomesticCurrency);
		int newUserBalance = userBalanceDomesticCurrency + add;
		userAccount.setAmount(Integer.toString(newUserBalance));
		userAccounts.add(userAccount);
		return userAccounts;
	}

	private boolean validateAmount(String amount) {
		try {
			int value = Integer.parseInt(amount);
			if (value < 0 ) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
