package com.chunarevsa.Website.service;

import java.util.Set;

import com.chunarevsa.Website.entity.Account;
import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.exception.NotEnoughResourcesException;
import com.chunarevsa.Website.repo.AccountRepository;
import com.chunarevsa.Website.service.inter.AccountServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountServiceInterface {

	private static final Logger logger = LogManager.getLogger(AccountService.class);

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
	@Override
	public Set<Account> buyCurrency(String currencyTitle, String amountDomesticCurrency, User user) {
	
		Set<Account> userAccounts = user.getAccounts();
		Account userAccount = userAccounts.stream()
				.filter(acc -> currencyTitle.equals(acc.getCurrencyTitle()))
				.findAny().orElse(null);
		
		if (userAccount == null) {
			Account newAccount = new Account();
			newAccount.setAmount(amountDomesticCurrency);
			newAccount.setCurrencyTitle(currencyTitle);
			Account savedAccount = accountRepository.save(newAccount);
			userAccounts.add(savedAccount);
			logger.info("Создан новый счёт " + user.getUsername() +" пользователя для " + currencyTitle);
		} else {
			int userBalanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
			int add = Integer.parseInt(amountDomesticCurrency);
			int newUserBalance = userBalanceDomesticCurrency + add;
			logger.info("Новый баланс пользователя " + user.getUsername() 
					+  " для валюты " + currencyTitle + " :" + Integer.toString(newUserBalance));
			userAccount.setAmount(Integer.toString(newUserBalance));
			userAccounts.add(userAccount);
		}
		return userAccounts;
	}
	/**
	 * Списание внутренней валюты при покупке Item
	 * @param userAccounts
	 * @param currencyTitle
	 * @param cost
	 * @param amountItems
	 */
	@Override
	public Set<Account> getNewUserAccounts(Set<Account> userAccounts, String currencyTitle,
			String cost, String amountItems) {

		Account userAccount = userAccounts.stream()
				.filter(acc -> currencyTitle.equals(acc.getCurrencyTitle())).findAny()
				.orElseThrow(() -> new NotEnoughResourcesException("Покупка", amountItems, currencyTitle));

		int itemCost =  Integer.parseInt(cost);
		int amountItemsInt = Integer.parseInt(amountItems);
		int balanceDomesticCurrency = Integer.parseInt(userAccount.getAmount());
		
		if (balanceDomesticCurrency < (itemCost*amountItemsInt)) {
			logger.error("У пользователя недостаточно валюты " +  currencyTitle + " для покупки");
			throw new NotEnoughResourcesException("Покупка", amountItems, currencyTitle);
		}

		String result =  Integer.toString(balanceDomesticCurrency - (itemCost*amountItemsInt));
		userAccount.setAmount(result);
		userAccounts.add(userAccount);
		logger.info("Новый баланс валюты " + currencyTitle + " " + result);
		return userAccounts;
	}

	
	
}
