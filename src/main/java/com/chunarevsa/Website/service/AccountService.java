package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
		Set<Account> userAcounts = user.getAccounts();
		Account userAccount = userAcounts.stream()
				.filter(acc -> currencyTitle.equals(acc.getCurrencyTitle()))
				.findAny().orElse(null);
		
		if (userAccount == null) {
			System.err.println("userAccount == null");
			Account newAccount = new Account();
			newAccount.setAmount(String.valueOf(1));
			newAccount.setCurrencyTitle(currencyTitle);
			Account savedAccount = accountRepository.save(newAccount);
			userAcounts.add(savedAccount);
			return userAcounts;
		}

		int userBalanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
		int add = Integer.parseInt(amountDomesticCurrency);
		int newUserBalance = userBalanceDomesticCurrency + add;
		userAccount.setAmount(Integer.toString(newUserBalance));
		userAcounts.add(userAccount);
		return userAcounts;

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
