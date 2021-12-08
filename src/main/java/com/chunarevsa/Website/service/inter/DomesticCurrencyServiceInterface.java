package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.payload.DomesticCurrencyRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;

import org.springframework.data.domain.Pageable;

public interface DomesticCurrencyServiceInterface {

	/**
	 * Получение всех Currency
	 * Если ADMIN -> page Currencies, если USER -> set CurrenciesDto
	 */
	public Object getCurrencies (Pageable pageable, JwtUser jwtUser);

	/**
	 * Получить Currency
	 * Если ADMIN -> Currency, если USER ->  CurrencyDto
	 */
	public Object getCurrency(String title, JwtUser jwtUser);

	/**
	 * Покупка валюты
	 * Списывается сумма со счёта пользователя
	 * Валюта добавляется в Account пользователю
	 */
	public Object buyCurrency(String currencyTitle, String amountDomesticCurrency, JwtUser jwtUser);

	/**
	 * Добавление Currency
	 */
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidPriceFormat;

	/**
	 * Изменение Currency
	 */
	public Optional<DomesticCurrency> editCurrency (String title, DomesticCurrencyRequest currencyRequest);

	/**
	 * Удаление (Выключение) Currency
	 */
	public void deleteCurrency(String title);

	/**
	 * Получение валюты по Title
	 */
	public Optional<DomesticCurrency> findCurrencyByTitile(String title);

	
}	

