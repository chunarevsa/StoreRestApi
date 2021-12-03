package com.chunarevsa.Website.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.dto.DomesticCurrencyDto;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;
import com.chunarevsa.Website.repo.DomesticCurrencyRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.DomesticCurrencyServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

// TODO: логирование
@Service
public class DomesticCurrencyService implements DomesticCurrencyServiceInterface {

	private final DomesticCurrencyRepository domesticCurrencyRepository;

	@Autowired
	public DomesticCurrencyService(
				DomesticCurrencyRepository domesticCurrencyRepository) {
		this.domesticCurrencyRepository = domesticCurrencyRepository;
	}

	public Object getCurrencies (Pageable pageable, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		if (roles.contains("ROLE_ADMIN")) {
			return getCurrenciesFromAdmin(pageable);
		} 
		return getCurrenciesDtoFromUser();
	}

	public Page<DomesticCurrency> getCurrenciesFromAdmin(Pageable pageable) {
		return findAllCurrency(pageable);
	}

	// Получение страницы всех Currency
	@Override
	public Set<DomesticCurrencyDto> getCurrenciesDtoFromUser () {
		Set<DomesticCurrency> currencies = findAllByActive(true);
		Set<DomesticCurrencyDto> currenciesDto = currencies.stream()
				.map(currency -> getCurrencyDto(currency.getId())).collect(Collectors.toSet());

		return currenciesDto;
	}

	// Получить CurrencyDto по title
	@Override
	public DomesticCurrencyDto getCurrencyDtoByTitle (String title) {
		DomesticCurrency currency = findCurrencyByTitile(title).get();
		return DomesticCurrencyDto.fromUser(currency);
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

	// Запись параметров
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
		return Optional.of(currency);  // mb не нужно, можно void

	}

	private DomesticCurrencyDto getCurrencyDto(Long id) {
		DomesticCurrency domesticCurrency = findById(id).get();
		return DomesticCurrencyDto.fromUser(domesticCurrency);
	}

	public Optional<DomesticCurrency> findCurrencyByTitile(String title) {
		System.out.println("findCurrencyByTitile");
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

	private DomesticCurrency saveCurrency(DomesticCurrency currency) {
		System.out.println("saveCurrency");
		return domesticCurrencyRepository.save(currency);
	}
	
}
