package com.chunarevsa.Website.service;

import java.util.List;
import java.util.Optional;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.dto.DomesticCurrencyDto;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;
import com.chunarevsa.Website.repo.DomesticCurrencyRepository;
import com.chunarevsa.Website.service.inter.DomesticCurrencyServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// TODO: Проверить логирование
@Service
public class DomesticCurrencyService implements DomesticCurrencyServiceInterface {

	private final DomesticCurrencyRepository domesticCurrencyRepository;
	//private final DomesticCurrencyValid domesticCurrencyValid;

	@Autowired
	public DomesticCurrencyService(
				DomesticCurrencyRepository domesticCurrencyRepository 
				//DomesticCurrencyValid domesticCurrencyValid
				) {
		this.domesticCurrencyRepository = domesticCurrencyRepository;
		//this.domesticCurrencyValid = domesticCurrencyValid;
	}

	// Создание
	@Override
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidPriceFormat {
		 
		DomesticCurrency newCurrency = new DomesticCurrency();
		newCurrency.setTitle(currencyRequest.getTitle());
		if (!validateCost(currencyRequest.getCost())) {
			throw new InvalidPriceFormat();
		}
		newCurrency.setCost(currencyRequest.getCost()); 
		newCurrency.setActive(currencyRequest.isActive());
		save(newCurrency);
		return Optional.of(newCurrency);
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

	private DomesticCurrency save(DomesticCurrency currency) {
		return domesticCurrencyRepository.save(currency);
	}

	// Получение по title
	public DomesticCurrencyDto getCurrencyDtoByTitle (String title) {
		DomesticCurrency currency = findByTitile(title).get();
		return DomesticCurrencyDto.fromUser(currency);
	}

	private Optional<DomesticCurrency> findByTitile(String title) {
		return domesticCurrencyRepository.findByTitle(title);
	}	

	// Запись параметров
	@Override
	public Optional<DomesticCurrency> overrideCurrency (String title, DomesticCurrencyRequest currencyRequest) {

		DomesticCurrency currency = findByTitile(title).get();
		currency.setTitle(currencyRequest.getTitle());
		currency.setCost(currencyRequest.getCost());
		currency.setActive(currencyRequest.isActive());
		save(currency);
		return Optional.of(currency);
	}

	@Override
	public void deleteCurrency(String title) {

		DomesticCurrency currency = findByTitile(title).get();
		currency.setActive(false);
		save(currency);

	}

	public Page<DomesticCurrency> getPage(Pageable pageable) {
		Page<DomesticCurrency> page = findAllByActive(true, pageable);
		return page;
	}

	private Page<DomesticCurrency> findAllByActive(boolean active, Pageable pageable) {
		return domesticCurrencyRepository.findAllByActive(active, pageable);
	}


}
