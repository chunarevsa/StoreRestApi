package com.chunarevsa.Website.service;

import java.util.Set;

import com.chunarevsa.Website.entity.Account;
import com.chunarevsa.Website.entity.User;
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

	/**
	 * Покупка валюты
	 * Проверяется есть ли такая валюта у пользователя
	 * Если нет, создаётся новый счёт
	 * Зачисляется новый баланс
	 */
	public Set<Account> buyCurrency(String currencyTitle, String amountDomesticCurrency, User user) {
	
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
			newAccount.setAmount(amountDomesticCurrency);
			newAccount.setCurrencyTitle(currencyTitle);
			Account savedAccount = accountRepository.save(newAccount);
			userAccounts.add(savedAccount);
		} else {
			int userBalanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
			int add = Integer.parseInt(amountDomesticCurrency);
			int newUserBalance = userBalanceDomesticCurrency + add;
			userAccount.setAmount(Integer.toString(newUserBalance));
			userAccounts.add(userAccount);
		}
		return userAccounts;
	}

	// Списание

	/**
	 * Проверка суммы 
	 */
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

	public Set<Account> getNewUserAccounts(
						Set<Account> userAccounts, 
						String currencyTitle,
						String cost,
						String amountItems) {

		Account userAccount = userAccounts.stream()
				.filter(acc -> currencyTitle.equals(acc.getCurrencyTitle()))
				.findAny().orElse(null);

		if (userAccount == null) {
			System.err.println("У вас нет такой валюты "); // TODO: искл
		}

		int itemCost =  Integer.parseInt(cost);
		int amountItemsInt = Integer.parseInt(amountItems);
		int balanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
		
		if (balanceDomesticCurrency < (itemCost*amountItemsInt)) {
			System.err.println("У вас не достаточно данной валюты на счёту");
		}

		String result =  Integer.toString(balanceDomesticCurrency - (itemCost*amountItemsInt));
		userAccount.setAmount(result);
		userAccounts.add(userAccount);
		return userAccounts;
	}
	
}
