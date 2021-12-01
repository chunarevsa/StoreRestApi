package com.chunarevsa.Website.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.NotFound;
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
public class PriceService implements PriceServiceInterface { // TODO: interface

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
	public Set<Price> getPriciesFromRequest (Set<PriceRequest> setPriciesRequests) {
		System.out.println("getSetPricies");

		Set<Price> pricies = setPriciesRequests.stream() // без связи с Item
				.map(priceRequest -> getPriceFromRequest(priceRequest))
				.collect(Collectors.toSet());
		
		//Set<Price> savedPricies = saveAll(pricies); // без связи с Item
		/* Set<Price> allPriceWithItem = pricies.stream()
				.map(savedPrice -> addPriceInItem(savedPrice, newItem) )
				.collect(Collectors.toSet()); */
		
		return pricies;
	}

	public Price getPriceFromRequest(PriceRequest priceRequest) {
		System.out.println("getPriceFromRequest");
		
		Price price = new Price();
		validatePriceRequest(priceRequest);
		price.setCost(priceRequest.getCost());
		price.setCurrencyTitle(priceRequest.getCurrency());
		price.setActive(priceRequest.getActive());

		return price;
	}

	public void savePricies(Item newItem) {
		System.out.println("savePricies");
		priceRepository.saveAll(newItem.getPrices());
	}

	public Price savePrice(Price newPrice) {
		Price price = priceRepository.save(newPrice);
		System.out.println("Save price :" + price);
		return price;
	}


	/* public Price addPriceInItem(Price price, Item item) {
		price.setItem(item);
		return price;
	} */

	/* private Set<Price> saveAll(Set<Price> pricies) {
		return priceRepository.saveAllPricies(pricies);
		// return (Set<Price>) priceRepository.saveAll(setPricies);
	} */

	

	private void validatePriceRequest(PriceRequest priceRequest) {

		if (!validateCostInPriceRequest(priceRequest.getCost())) {
			System.err.println("Сумма " + priceRequest.getCost() + " заполнена не верно");
		}

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency()).get();
		if (domesticCurrency == null) {
			System.err.println("Валюта" + priceRequest + " заполнена не верно");
		} 

		if (!priceRequest.isActive()){
			System.err.println("Цена " + priceRequest.isActive() + " выключена");
		}
		
	}

	public boolean validateCostInPriceRequest(String cost) {
		int i = Integer.parseInt(cost);
		if (i < 0 ) {
			return false;
		}
		return true;
	}

	


	/* public Price getPriceFromRequest(PriceRequest priceRequest, Item item) { // TODO: mb optional
		// TODO: сделать price active из реквества а не true
		System.out.println("getPriceFromRequest");
		Price price = new Price();

		if (!validateCostInPriceRequest(priceRequest.getCost())) {
			price.setActive(false);
			System.err.println("Сумма " + priceRequest.getCost() + "заполнена не верно");
			return price;
		}	
		price.setCost(priceRequest.getCost());

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency()).get();
		if (domesticCurrency == null) {
			price.setActive(false);
			System.err.println("Валюта" + priceRequest + "заполнена не верно");
			return price;
		}
		price.setCurrencyTitle(priceRequest.getCurrency());

		try {
			price.getItem();
			price.setItem(item);

			System.out.println("price.getItem() is :" + price.getItem());
		} catch (Exception e) {
			System.err.println("ОШИБКА в setItem");
			price.setActive(false);
			return price;
		} 

		price.setActive(true);
		return price;

	} */


	// Удаление цены
	// Пока не используется 

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
