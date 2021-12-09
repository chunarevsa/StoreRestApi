package com.chunarevsa.Website.service.inter;

import java.util.Set;

import com.chunarevsa.Website.entity.Account;
import com.chunarevsa.Website.entity.User;

public interface AccountServiceInterface {
	
	/**
	 * Покупка валюты
	 * Проверяется есть ли такая валюта у пользователя
	 * Если нет, создаётся новый счёт
	 * Зачисляется новый баланс
	 */
	public Set<Account> buyCurrency(String currencyTitle, String amountDomesticCurrency, User user);

	/**
	 * Списание внутренней валюты при покупке Item
	 */
	public Set<Account> getNewUserAccounts(Set<Account> userAccounts, String currencyTitle,
			String cost, String amountItems);
	
}
