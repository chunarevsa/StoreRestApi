package com.chunarevsa.website.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.website.dto.AccountDto;
import com.chunarevsa.website.dto.DomesticCurrencyDto;
import com.chunarevsa.website.entity.UserAccount;
import com.chunarevsa.website.entity.Currency;
import com.chunarevsa.website.entity.User;
import com.chunarevsa.website.exception.AlreadyUseException;
import com.chunarevsa.website.exception.InvalidAmountFormat;
import com.chunarevsa.website.exception.NotEnoughResourcesException;
import com.chunarevsa.website.exception.ResourceNotFoundException;
import com.chunarevsa.website.payload.DomesticCurrencyRequest;
import com.chunarevsa.website.security.jwt.JwtUser;
import com.chunarevsa.website.service.inter.DomesticCurrencyServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


@Service
public class CurrencyService {

	private static final Logger logger = LogManager.getLogger(CurrencyService.class);

	private final UserService userService;
	private final AccountService accountService;

	@Autowired
	public CurrencyService(
				
				UserService userService,
				AccountService accountService) {
		
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
		DomesticCurrency domesticCurrency = findCurrencyByTitile(title);
		if (!domesticCurrency.isActive()){
			logger.error("Currency  " + title + " не активен");
			throw new ResourceNotFoundException("Price", "active", true);
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
		
		DomesticCurrency domesticCurrency = findCurrencyByTitile(currencyTitle);
		User user = userService.findByUsername(jwtUser.getUsername().toString());
		double userBalance = Math.round(Double.parseDouble(user.getBalance()));
		
		if (!validateAmountFromCurrency(amountDomesticCurrency)) {
			logger.error("Не выерный формат суммы валюты " + currencyTitle);
			throw new InvalidAmountFormat("Сумма", currencyTitle, amountDomesticCurrency);
		}

		double costCurrency = Math.round(Double.parseDouble(domesticCurrency.getCost()));
		double amountDomesticCurrencyInt = Math.round(Double.parseDouble(amountDomesticCurrency));
		double sum = (costCurrency * amountDomesticCurrencyInt);

		if (userBalance < sum) {
			logger.info("Суммы баланса пользователя " + user.getUsername() + "не достаточно для покупки валюты " + currencyTitle);
			throw new NotEnoughResourcesException("Покупка", Double.toString(sum), "$");
		}

		double newUserBalance = Math.round(userBalance - sum);
		user.setBalance(Double.toString(newUserBalance));

		Set<UserAccount> accounts = accountService.buyCurrency(currencyTitle, amountDomesticCurrency, user);
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
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) {
		
		if (currencyAlredyExists(currencyRequest.getTitle())) {
			logger.error("Валюта " + currencyRequest.getTitle() + " уже существует");
			throw new AlreadyUseException("Валюта", "title", currencyRequest.getTitle());
		}

		if (!validateCostCurrency(currencyRequest.getCost())) {
			logger.error("Неверный формат цены " + currencyRequest.getCost());
			throw new InvalidAmountFormat("Стоимость", "currncy", currencyRequest.getCost());
		}

		DomesticCurrency newCurrency = new DomesticCurrency();
		newCurrency.setTitle(currencyRequest.getTitle());
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
		
		DomesticCurrency currency = findCurrencyByTitile(title);
		

		if (!currencyRequest.getTitle().equals(title) && currencyAlredyExists(currencyRequest.getTitle())) {
				logger.error("Валюта " + currencyRequest.getTitle() + " уже существует");
				throw new AlreadyUseException("Валюта", "title", currencyRequest.getTitle());
		}
		
		if (!validateCostCurrency(currencyRequest.getCost())) {
			logger.error("Неверный формат цены " + currencyRequest.getCost());
			throw new InvalidAmountFormat("Стоимость", "currncy", currencyRequest.getCost());
		}

		currency.setTitle(currencyRequest.getTitle());
		currency.setCost(currencyRequest.getCost());
		currency.setActive(currencyRequest.isActive());
		saveCurrency(currency);
		logger.info("Валюта " + title + " изменена");
		return Optional.of(currency);
	} 

	/**
	 * Удаление (Выключение) Currency
	 */
	@Override
	public void deleteCurrency(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title);
		currency.setActive(false);
		saveCurrency(currency);
		logger.info("Валюта " + title + " выключена");
	}
	/**
	 * Получение валюты по Title
	 */

	public Currency findCurrencyByTitile(String title) {
		return domesticCurrencyRepository.findByTitle(title)
			.orElseThrow(() -> new ResourceNotFoundException("Валюта", "title", title));
	}

	/**
	 * Получение списка DomesticCurrencyDto для USER
	 */
	private Set<DomesticCurrencyDto> getCurrenciesDtoFromUser () {
		Set<DomesticCurrency> currencies = findAllByActive(true);
		if (currencies.isEmpty()) {
			logger.info("Нет активных Item");
			throw new ResourceNotFoundException("Currencies", "active", true);
		}
		Set<DomesticCurrencyDto> currenciesDto = currencies.stream()
				.map(currency -> getCurrencyDto(currency.getId())).collect(Collectors.toSet());
		return currenciesDto;
	}

	/**
	 * Получить CurrencyDto по title
	 */
	private DomesticCurrencyDto getCurrencyDtoByTitle(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title);
		if (!currency.isActive()) {
			logger.error("Валюта " + currency.getTitle() + " не активна");
			throw new ResourceNotFoundException("Currency", "active", true);
		}
		return DomesticCurrencyDto.fromUser(currency);
	}

	/**
	 * Получение страницы всех DomesticCurrency
	 */
	private Page<DomesticCurrency> getCurrenciesFromAdmin(Pageable pageable) {
		return findAllCurrency(pageable);
	}

	/**
	 * Получение DomesticCurrencyDto
	 */
	private DomesticCurrencyDto getCurrencyDto(Long id) {
		DomesticCurrency domesticCurrency = findById(id);
		if (!domesticCurrency.isActive()) {
			logger.error("Нет активных валют");
			throw new ResourceNotFoundException("Currency", "active", true);
		}
		return DomesticCurrencyDto.fromUser(domesticCurrency);
	}

	private Page<DomesticCurrency> findAllCurrency(Pageable pageable) {
		return domesticCurrencyRepository.findAll(pageable);
	}

	private Set<DomesticCurrency> findAllByActive(boolean active) {
		Set<DomesticCurrency> activeCurrency = domesticCurrencyRepository.findAllByActive(active);
		if (activeCurrency == null) {
			logger.error("Нет активных валют");
			throw new ResourceNotFoundException("currency", "active", true);
		}
		return activeCurrency;
	} 

	private DomesticCurrency findById(Long id) {
		return domesticCurrencyRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Валюта", "id", id));
	} 

	private DomesticCurrency saveCurrency(DomesticCurrency currency) {
		return domesticCurrencyRepository.save(currency);
	}

	private boolean currencyAlredyExists(String title) {
		return domesticCurrencyRepository.existsByTitle(title);
	}

	/**
	 * Проверка суммы 
	 */
	private boolean validateAmountFromCurrency(String amount) {
		try {
			int value = Integer.parseInt(amount);
			if (value <= 0 ) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) { 
			return false;
		}
	}

	private boolean validateCostCurrency(String cost) {
		try {
			double numb = Double.parseDouble(cost);
			if (numb <= 0) {
				return false;
			}
			return true;

		} catch (NumberFormatException e) {
			return false;
		}

	}
	
}
