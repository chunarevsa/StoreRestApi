package com.chunarevsa.website.service.inter;

import java.util.Set;

import com.chunarevsa.website.entity.UserAccount;
import com.chunarevsa.website.entity.User;

public interface AccountServiceInterface {
	
	/**
	 * Покупка валюты
	 * Проверяется есть ли такая валюта у пользователя
	 * Если нет, создаётся новый счёт
	 * Зачисляется новый баланс
	 */
	public Set<UserAccount> buyCurrency(String currencyTitle, String amountDomesticCurrency, User user);

	/**
	 * Списание внутренней валюты при покупке Item
	 */
	public Set<UserAccount> getNewUserAccounts(Set<UserAccount> userAccounts, String currencyTitle,
			String cost, String amountItems);
	
}
