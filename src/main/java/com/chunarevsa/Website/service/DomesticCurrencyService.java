package com.chunarevsa.Website.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.dto.AccountDto;
import com.chunarevsa.Website.dto.DomesticCurrencyDto;
import com.chunarevsa.Website.entity.Account;
import com.chunarevsa.Website.entity.DomesticCurrency;
import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.exception.InvalidAmountFormat;
import com.chunarevsa.Website.exception.NotEnoughResourcesException;
import com.chunarevsa.Website.payload.DomesticCurrencyRequest;
import com.chunarevsa.Website.repo.DomesticCurrencyRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.DomesticCurrencyServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


@Service
public class DomesticCurrencyService implements DomesticCurrencyServiceInterface {

	private static final Logger logger = LogManager.getLogger(DomesticCurrencyService.class);

	private final DomesticCurrencyRepository domesticCurrencyRepository;
	private final UserService userService;
	private final AccountService accountService;

	@Autowired
	public DomesticCurrencyService(
				DomesticCurrencyRepository domesticCurrencyRepository,
				UserService userService,
				AccountService accountService) {
		this.domesticCurrencyRepository = domesticCurrencyRepository;
		this.userService = userService;
		this.accountService = accountService;
	}

	/**
	 * Получение всех Currency
	 * Если ADMIN -> page Currencies, если USER -> set CurrenciesDto
	 */
	@Override
	public Object getCurrencies (Pageable pageable, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return getCurrenciesFromAdmin(pageable);
		} 
		return getCurrenciesDtoFromUser();
	}

	/**
	 * Получить Currency
	 * Если ADMIN -> Currency, если USER ->  CurrencyDto
	 */
	@Override
	public Object getCurrency(String title, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return findCurrencyByTitile(title);
		} 
		return getCurrencyDtoByTitle(title);
	}

	/**
	 * Покупка валюты
	 * Списывается сумма со счёта пользователя
	 * Валюта добавляется в Account пользователю
	 * @param currencyTitle
	 * @param amountDomesticCurrency
	 * @param jwtUser
	 */

	public Object buyCurrency(String currencyTitle, String amountDomesticCurrency, JwtUser jwtUser) {
		
		DomesticCurrency domesticCurrency = findCurrencyByTitile(currencyTitle).get();
		User user = userService.findByUsername(jwtUser.getUsername().toString()).get();
		double userBalance = Math.round(Double.parseDouble(user.getBalance()));

		double costCurrency = Math.round(Double.parseDouble(domesticCurrency.getCost()));
		double amountDomesticCurrencyInt = Math.round(Double.parseDouble(amountDomesticCurrency));
		double sum = (costCurrency * amountDomesticCurrencyInt);

		if (userBalance < sum) {
			logger.info("Суммы баланса пользователя " + user.getUsername() + "не достаточно для покупки валюты " + currencyTitle);
			throw new NotEnoughResourcesException("Покупка", Double.toString(sum), currencyTitle);
		}

		double newUserBalance = Math.round(userBalance - sum);
		user.setBalance(Double.toString(newUserBalance));

		Set<Account> accounts = accountService.buyCurrency(currencyTitle, amountDomesticCurrency, user);
		user.setAccounts(accounts);
		userService.saveUser(user);
		logger.info("Пользователь " + user.getUsername() + " приобрел валюту " 
				+ currencyTitle + "в размере " + amountDomesticCurrency);
		
		return user.getAccounts().stream()
				.map(account -> AccountDto.fromUser(account)).collect(Collectors.toSet());

	}

	/**
	 * Добавление Currency
	 */
	@Override
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidAmountFormat {
		
		DomesticCurrency newCurrency = new DomesticCurrency();
		newCurrency.setTitle(currencyRequest.getTitle());
		if (!validateCost(currencyRequest.getCost())) {
			logger.error("Неверный формат цены " + currencyRequest.getCost());
			throw new InvalidAmountFormat("Цена", newCurrency.getTitle(), currencyRequest.getCost());
		}
		newCurrency.setCost(currencyRequest.getCost()); 
		newCurrency.setActive(currencyRequest.isActive());
		saveCurrency(newCurrency);
		logger.info("Добавлена валюта  " + newCurrency.getTitle());
		return Optional.of(newCurrency);
	}

	/**
	 * Изменение Currency
	 */
	@Override
	public Optional<DomesticCurrency> editCurrency (String title, DomesticCurrencyRequest currencyRequest) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		currency.setTitle(currencyRequest.getTitle());
		currency.setCost(currencyRequest.getCost());
		currency.setActive(currencyRequest.isActive());
		saveCurrency(currency);
		logger.info("Валюта " + currency.getTitle() + " изменена");
		return Optional.of(currency);
	}

	/**
	 * Удаление (Выключение) Currency
	 */
	@Override
	public void deleteCurrency(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		currency.setActive(false);
		saveCurrency(currency);
		logger.info("Валюта " + title + " выключена");
	}
	/**
	 * Получение валюты по Title
	 */
	@Override
	public Optional<DomesticCurrency> findCurrencyByTitile(String title) {
		return domesticCurrencyRepository.findByTitle(title);
	}

	/**
	 * Получение списка DomesticCurrencyDto для USER
	 */
	private Set<DomesticCurrencyDto> getCurrenciesDtoFromUser () {
		Set<DomesticCurrency> currencies = findAllByActive(true);
		Set<DomesticCurrencyDto> currenciesDto = currencies.stream()
				.map(currency -> getCurrencyDto(currency.getId()).get()).collect(Collectors.toSet());
		return currenciesDto;
	}

	/**
	 * Получить CurrencyDto по title
	 */
	private Optional<DomesticCurrencyDto> getCurrencyDtoByTitle(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		return Optional.of(DomesticCurrencyDto.fromUser(currency));
	}

	/**
	 * Получение страницы всех DomesticCurrency
	 */
	private Page<DomesticCurrency> getCurrenciesFromAdmin(Pageable pageable) {
		return findAllCurrency(pageable);
	}

	/**
	 * Получение всех DomesticCurrencyDto
	 */
	private Optional<DomesticCurrencyDto> getCurrencyDto(Long id) {
		DomesticCurrency domesticCurrency = findById(id).get();
		return  Optional.of(DomesticCurrencyDto.fromUser(domesticCurrency));
	}

	private Page<DomesticCurrency> findAllCurrency(Pageable pageable) {
		return domesticCurrencyRepository.findAll(pageable);
	}

	private Set<DomesticCurrency> findAllByActive(boolean active) {
		return domesticCurrencyRepository.findAllByActive(active);
	} 

	private Optional<DomesticCurrency> findById(Long id) {
		return domesticCurrencyRepository.findById(id);
	} 

	private Optional<DomesticCurrency> saveCurrency(DomesticCurrency currency) {
		return  Optional.of(domesticCurrencyRepository.save(currency)) ;
	}

	private boolean validateCost(String cost) {

		try {
			// double num = Double.valueOf(cost);
			double numb = Double.parseDouble(cost);
			if (numb < 0) {
				return false;
			}
			return true;
		} catch (Exception e) { // TODO: валидация
			return false;
		}

	}
	
}
