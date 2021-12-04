package com.chunarevsa.Website.service;

import java.util.Optional;

import com.chunarevsa.Website.Entity.Account;
import com.chunarevsa.Website.repo.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	//private final DomesticCurrencyService domesticCurrencyService;

	@Autowired
	public AccountService(AccountRepository accountRepository
	//, DomesticCurrencyService domesticCurrencyService
	) {
		this.accountRepository = accountRepository;
		//this.domesticCurrencyService = domesticCurrencyService;
	}

	public Optional<Account> byeCurrency(String currencyTitle, String amount) {
		System.err.println("currencyTitle is :" +currencyTitle);
		System.err.println("amount is "+ amount);

		Account account = findByCurrencyTitle(currencyTitle);
		
		//System.out.println("account is :" + account.toString() );
		
		if (account == null) {
			System.err.println("TYT");
			account = new Account(); 
			account.setAmount(String.valueOf(0));
			account.setCurrencyTitle(currencyTitle);   // TODO: проверить
		}
		
		// Если нет account то создать
		if (!validateAmount(amount)) {
			System.err.println("ОШИБКА ВАЛИДАЦИИ"); // TODO: исключение
		}
		System.err.println("TYT2");
		int userBalance = Integer.parseInt(account.getAmount());
		int add = Integer.parseInt(amount);
		int newUserBalance = userBalance + add;
		System.err.println( "new User Domestic currency Balance is :" + newUserBalance);

		account.setAmount(Integer.toString(newUserBalance));
		System.out.println("account.getAmount() :" + account.getAmount());
		return saveAсcount(account);
		
	}

	private Optional<Account> saveAсcount(Account account) {
		return Optional.of(accountRepository.save(account));
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

	private Account findByCurrencyTitle(String currencyTitle) {
		return accountRepository.findByCurrencyTitle(currencyTitle);
	}
	
}
