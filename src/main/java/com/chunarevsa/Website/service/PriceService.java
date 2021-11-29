package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.inter.PriceServiceInterface;
import com.chunarevsa.Website.service.valid.PriceValid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PriceService implements PriceServiceInterface {

	private final PriceRepository priceRepository;
	private final DomesticCurrencyService domesticCurrencyService;
	private final PriceValid priceValid;

	@Autowired
	public PriceService(
				PriceRepository priceRepository,
				DomesticCurrencyService domesticCurrencyService,
				PriceValid priceValid) {
		this.priceRepository = priceRepository;
		this.domesticCurrencyService = domesticCurrencyService;
		this.priceValid = priceValid;
	}

	// Сохранение всех цен
	@Override
	public void saveAllPrice(Item bodyItem) throws InvalidPriceFormat, FormIsEmpty, NotFoundDomesticCurrency {
		
		Set<Price> pricesSet = bodyItem.getPrices();
		int i = 1;
		try {
		for (Price price : pricesSet) {
			 if (!priceValid.amountIsCorrect(price)) {
				log.warn("IN saveAllPrice.amountIsCorrect - price {} amount is NOT correct ", i);
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			} 
			
			if (priceValid.bodyIsEmpty(price)) {
				log.warn("IN saveAllPrice.bodyIsEmpty - price {} amount is NOT correct ", i);
				throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
			} 

			/* boolean codeIsPresent = currencyValid.codeIsPresent(price.getCurrencyCode());
			System.out.println(codeIsPresent);
			if (!codeIsPresent) {
				log.warn("IN saveAllPrice.codeIsPresent - price {} currency is NOT correct", i);
				throw new NotFoundDomesticCurrency(HttpStatus.NOT_FOUND);
			}  */

			price.setActive(true);
			
			log.info("IN saveAllPrice - price {} is correct", i);
			i++;
		}	
		} catch (NumberFormatException e) {
			throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
		}
		priceRepository.saveAll(bodyItem.getPrices());
		log.info("IN All Price is correct");
	}

	// Удаление цены
	// Пока не используется 
	@Override
	public void deletePrice (Long id) throws NotFound {
		// Проверка на наличие 
		if (!priceValid.priceIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Удаляем только в случае active 
		if (!priceValid.priceIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		Price price = priceRepository.findById(id).orElseThrow();
		price.setActive(false);
		priceRepository.save(price);
		log.info("IN delete - item with id: {} successfully deleted", id, price);
		 
	}

	public boolean validatePriceRequest(PriceRequest priceRequest) {
		int i = Integer.parseInt(priceRequest.getCost());
		if (i < 0) {
			return false;
		}
		return true;
	}

	public Price getPriceFromRequest(PriceRequest priceRequest) throws InvalidPriceFormat, NotFoundDomesticCurrency { // TODO: mb optional
		Price price = new Price();
		if (!validatePriceRequest(priceRequest)) {
			throw new InvalidPriceFormat();
		}
		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrencyTitle()).get();
		if (domesticCurrency == null) {
			throw new NotFoundDomesticCurrency();
		}
		price.setActive(true);
		price.setCurrencyTitle(priceRequest.getCurrencyTitle());
		price.setCost(priceRequest.getCost());
		
				
		
		return null;
	}

	// Сохранение конкретной цены - доделать

	// Удаление цены - доделать

	// Получение модели цены - доделать
	
}
