package com.chunarevsa.Website.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.Account;
import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.dto.DomesticCurrencyDto;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;
import com.chunarevsa.Website.repo.DomesticCurrencyRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.DomesticCurrencyServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

// TODO: логирование
@Service
public class DomesticCurrencyService implements DomesticCurrencyServiceInterface {

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

	@Override
	public Object getCurrencies (Pageable pageable, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return getCurrenciesFromAdmin(pageable);
		} 
		return getCurrenciesDtoFromUser();
	}

	@Override
	public Object getCurrency(String title, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return findCurrencyByTitile(title);
		} 
		return getCurrencyDtoByTitle(title);
	}

	public Object byeCurrency(String currencyTitle, String amountDomesticCurrency, JwtUser jwtUser) {
		
		User user = userService.findByUsername(jwtUser.getUsername().toString()).get();
		double userBalance = Math.round(Double.parseDouble(user.getBalance()));

		DomesticCurrency domesticCurrency = findCurrencyByTitile(currencyTitle).get();

		double costCurrency = Math.round(Double.parseDouble(domesticCurrency.getCost()));
		double amountDomesticCurrencyInt = Math.round(Double.parseDouble(amountDomesticCurrency));

		if (userBalance < (costCurrency * amountDomesticCurrencyInt)) {
			System.err.println("Суммы не достаточно для покупки "); // TODO: искл
			return ResponseEntity.badRequest().body("Суммы не достаточно для покупки ");
		}

		double newUserBalance = Math.round(userBalance - (costCurrency * amountDomesticCurrencyInt));
		user.setBalance(Double.toString(newUserBalance));

		Set<Account> accounts = accountService.byeCurrency(currencyTitle, amountDomesticCurrency, user);
		user.setAccounts(accounts);
		userService.saveUser(user);
		System.err.println("user balance is :" + user.getBalance());
		return user.getAccounts();
	}

	// Добавление Currency	
	@Override
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidPriceFormat {
		
		DomesticCurrency newCurrency = new DomesticCurrency();
		newCurrency.setTitle(currencyRequest.getTitle());
		if (!validateCost(currencyRequest.getCost())) {
			throw new InvalidPriceFormat(); // TODO: исключение
		}
		newCurrency.setCost(currencyRequest.getCost()); 
		newCurrency.setActive(currencyRequest.isActive());
		saveCurrency(newCurrency);
		return Optional.of(newCurrency);
	}

	// Изменение Currency
	@Override
	public Optional<DomesticCurrency> editCurrency (String title, DomesticCurrencyRequest currencyRequest) {
		System.out.println("editCurrency");
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		currency.setTitle(currencyRequest.getTitle());
		currency.setCost(currencyRequest.getCost());
		currency.setActive(currencyRequest.isActive());
		saveCurrency(currency);
		return Optional.of(currency);
	}

	// Удаление (Выключение) Currency
	@Override
	public Optional<DomesticCurrency> deleteCurrency(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		currency.setActive(false);
		saveCurrency(currency);
		return Optional.of(currency);  
	}

	// Получение списка DomesticCurrencyDto для USER
	private Set<DomesticCurrencyDto> getCurrenciesDtoFromUser () {
		Set<DomesticCurrency> currencies = findAllByActive(true);
		Set<DomesticCurrencyDto> currenciesDto = currencies.stream()
				.map(currency -> getCurrencyDto(currency.getId()).get()).collect(Collectors.toSet());
		return currenciesDto;
	}

	// Получить CurrencyDto по title
	private Optional<DomesticCurrencyDto> getCurrencyDtoByTitle(String title) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		return Optional.of(DomesticCurrencyDto.fromUser(currency));
	}

	private Page<DomesticCurrency> getCurrenciesFromAdmin(Pageable pageable) {
		return findAllCurrency(pageable);
	}

	private Optional<DomesticCurrencyDto> getCurrencyDto(Long id) {
		DomesticCurrency domesticCurrency = findById(id).get();
		return  Optional.of(DomesticCurrencyDto.fromUser(domesticCurrency));
	}

	@Override
	public Optional<DomesticCurrency> findCurrencyByTitile(String title) {
		return domesticCurrencyRepository.findByTitle(title);
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

	private boolean validateCost(String cost) {
		System.out.println("validateCost");

		try {
			double num = Double.valueOf(cost);
			System.out.println("num is " + num);
			double numb = Double.parseDouble(cost);
			System.out.println("numb is "+ numb);
			if (numb < 0) {
				return false;
			}
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	private Optional<DomesticCurrency> saveCurrency(DomesticCurrency currency) {
		System.out.println("saveCurrency");
		return  Optional.of(domesticCurrencyRepository.save(currency)) ;
	}

	
}
