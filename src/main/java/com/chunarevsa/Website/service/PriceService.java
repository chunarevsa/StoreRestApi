package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.inter.PriceServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService implements PriceServiceInterface { // TODO: interface

	private final PriceRepository priceRepository;
	private final DomesticCurrencyService domesticCurrencyService;

	@Autowired
	public PriceService(
				PriceRepository priceRepository,
				DomesticCurrencyService domesticCurrencyService) {
		this.priceRepository = priceRepository;
		this.domesticCurrencyService = domesticCurrencyService;
	}

	// Получение всех цен в PriceDto
	@Override
	public Set<PriceDto> getItemPriciesDto(Set<Price> pricies) {
		
		//Set<Price> pricies = findAllByActive(true);
		pricies.removeIf(price -> price.getActive() == false);
		Set<PriceDto> priciesDto = pricies.stream()
				.map(price -> getItemPriceDto(price.getId())).collect(Collectors.toSet());
		
		return priciesDto;
	}

	// Получение цены в PriceDto
	@Override
	public PriceDto getItemPriceDto(Long priceId) {
		Price price = findById(priceId).get();
		return PriceDto.fromUser(price);
	}

	// Получение списка Price из списка PriceRequest
	@Override
	public Set<Price> getItemPriciesFromRequest (Set<PriceRequest> priciesRequest) {
		System.out.println("getSetPricies");

		Set<Price> pricies = priciesRequest.stream() // без связи с Item
				.map(priceRequest -> getItemPriceFromRequest(priceRequest))
				.collect(Collectors.toSet());
		
		return pricies;
	}

	// Получение Price из PriceRequest
	private Price getItemPriceFromRequest(PriceRequest priceRequest) {
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

	// Валидация PriceRequest
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

	// Валидация cost
	private boolean validateCostInPriceRequest(String cost) {
		System.out.println("validateCostInPriceRequest");
		int i = Integer.parseInt(cost);
		if (i < 0 ) {
			return false;
		}
		return true;
	}

	// Изменение цены
	@Override
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {
		
		Price price = findPriceById(priceId);
		if (price == null) {
			System.err.println("ЦЕНА НЕ НАЙДЕЙНА, ДОБАВИТЬ ИСКЛЮЧЕНИЕ");
		}
		Price newPrice = getItemPriceFromRequest(priceRequest);
		price.setCost(newPrice.getCost());
		price.setCurrencyTitle(newPrice.getCurrencyTitle());
		price.setActive(newPrice.getActive());
		
		return Optional.of(savePrice(price));
	}

	// Удаление всех цен
	@Override
	public Set<Price> deletePricies (Set<Price> prices) {
		return prices.stream()
				.map(price -> deletePrice(price.getId())).collect(Collectors.toSet());
	}

	// Удаление цены
	private Price deletePrice (Long id) {
		Price price = priceRepository.findById(id).orElseThrow();
		price.setActive(false);
		priceRepository.save(price);
		return price;
	}

	// Сохранение всех цен
	@Override
	public Set<Price> savePricies(Set<Price> pricies) {
		System.out.println("savePricies");
		return pricies.stream().map(price -> savePrice(price)).collect(Collectors.toSet());
	}

	// Сохранение цены
	private Price savePrice(Price newPrice) {
		System.out.println("savePrice");
		Price price = priceRepository.save(newPrice);
		return price;
	}

	private Price findPriceById(Long priceId) {
		return priceRepository.findById(priceId).orElseThrow(null);
	}

	private Optional<Price> findById (Long id) {
		return priceRepository.findById(id);
	}

	private Set<Price> findAllByActive(boolean active) {
		return priceRepository.findAllByActive(active);
	}
	
}
