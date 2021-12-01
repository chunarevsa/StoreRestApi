package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.inter.PriceServiceInterface;
import com.chunarevsa.Website.service.valid.PriceValid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
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
		
		return pricies;
	}

	public Price getPriceFromRequest(PriceRequest priceRequest) {
		System.out.println("getPriceFromRequest");
		
		Price price = new Price();
		price.setCost(priceRequest.getCost());
		price.setCurrencyTitle(priceRequest.getCurrency());

		if  (!validatePriceRequest(priceRequest)) {
			price.setActive(false);
		}
		price.setActive(priceRequest.getActive());

		return price;
	}

	private boolean validatePriceRequest(PriceRequest priceRequest) {
		System.out.println("validatePriceRequest");
		if (!validateCostInPriceRequest(priceRequest.getCost())) {
			System.err.println("Сумма " + priceRequest.getCost() + " заполнена не верно");
			priceRequest.setActive(false);
		}

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency()).get();
		if (domesticCurrency == null) {
			System.err.println("Валюта" + priceRequest + " заполнена не верно");
			priceRequest.setActive(false);
		} 

		if (!priceRequest.isActive()){
			System.err.println("Цена " + priceRequest.isActive() + " выключена");
			return false;
		}
		return true;
	}

	public boolean validateCostInPriceRequest(String cost) {
		System.out.println("validateCostInPriceRequest");
		int i = Integer.parseInt(cost);
		if (i < 0 ) {
			return false;
		}
		return true;
	}

	public void savePricies(Item newItem) {
		System.out.println("savePricies");
		priceRepository.saveAll(newItem.getPrices());
	}

	public Price savePrice(Price newPrice) {
		System.out.println("savePrice");
		Price price = priceRepository.save(newPrice);
		return price;
	}

	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {
		
		Price price = findPriceById(priceId);
		if (price == null) {
			System.err.println("ЦЕНА НЕ НАЙДЕЙНА, ДОБАВИТЬ ИСКЛЮЧЕНИЕ");
		}
		Price newPrice = getPriceFromRequest(priceRequest);
		price.setCost(newPrice.getCost());
		price.setCurrencyTitle(newPrice.getCurrencyTitle());
		price.setActive(newPrice.getActive());
		
		return Optional.of(savePrice(price));
	}

	public Price findPriceById(Long priceId) {
		return priceRepository.findById(priceId).orElseThrow(null);
	}
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
		 
	}

	public Set<PriceDto> getItemPriciesDto(Long itemId) {
		
		Set<Price> pricies = findAllByActive(true);
		pricies.removeIf
		Set<PriceDto> priciesDto = pricies.stream()
				.map(price -> getPriceDto(price.getId())).collect(Collectors.toSet());
		
		return priciesDto;
	}

	public PriceDto getPriceDto(Long id) {
		Price price = findById(id).get();
		return PriceDto.fromUser(price);
	}

	public Optional<Price> findById (Long id) {
		return priceRepository.findById(id);
	}

	private Set<Price> findAllByActive(boolean active) {
		return priceRepository.findAllByActive(active);
	}

	public Set<Price> getPagePricies(Long itemId, Pageable pageable) {
		
		return priceRepository.findAllByItem(itemId, pageable);
	}
	

	

	// Удаление цены - доделать

	// Получение модели цены - доделать
	
}
