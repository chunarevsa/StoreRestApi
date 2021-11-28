package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.inter.PriceServiceInterface;
import com.chunarevsa.Website.service.valid.DomesticCurrencyValid;
import com.chunarevsa.Website.service.valid.PriceValid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PriceService implements PriceServiceInterface {

	private final PriceRepository priceRepository;
	private final PriceValid priceValid;
	private final DomesticCurrencyValid currencyValid;

	public PriceService(
				PriceRepository priceRepository,
				PriceValid priceValid, 
				DomesticCurrencyValid currencyValid) {
		this.priceRepository = priceRepository;
		this.priceValid = priceValid;
		this.currencyValid = currencyValid;
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

	// Сохранение конкретной цены - доделать

	// Удаление цены - доделать

	// Получение модели цены - доделать
	
}
