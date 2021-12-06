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
public class PriceService implements PriceServiceInterface { 

	private final PriceRepository priceRepository;
	private final DomesticCurrencyService domesticCurrencyService;

	@Autowired
	public PriceService(
				PriceRepository priceRepository,
				DomesticCurrencyService domesticCurrencyService) {
		this.priceRepository = priceRepository;
		this.domesticCurrencyService = domesticCurrencyService;
	}

	// Получение списка Price из списка PriceRequest
	@Override
	public Set<Price> getItemPriciesFromRequest (Set<PriceRequest> priciesRequest) {

		Set<Price> pricies = priciesRequest.stream() // без связи с Item
				.map(priceRequest -> getItemPriceFromRequest(priceRequest).get())
				.collect(Collectors.toSet());
		return pricies;
	}

	// Сохранение всех цен
	@Override
	public Set<Price> savePricies(Set<Price> pricies) {
		return pricies.stream().map(price -> savePrice(price).get()).collect(Collectors.toSet());
	}

	// Изменение цены
	@Override
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {

		Price price = findPriceById(priceId);
		if (price == null) {
			System.err.println("ЦЕНА НЕ НАЙДЕЙНА, ДОБАВИТЬ ИСКЛЮЧЕНИЕ");
		}
		Price newPrice = getItemPriceFromRequest(priceRequest).get();
		price.setCost(newPrice.getCost());
		price.setCurrencyTitle(newPrice.getCurrencyTitle());
		price.setActive(newPrice.getActive());
		return savePrice(price);
	}

	// Удаление всех цен @
	@Override
	public Set<Price> deletePricies (Set<Price> prices) { 
		return prices.stream()
				.map(price -> deletePrice(price.getId()).get()).collect(Collectors.toSet());
	}

	// Удаление цены
	private Optional<Price> deletePrice (Long id) {

		Price price = priceRepository.findById(id).orElseThrow();
		price.setActive(false);
		priceRepository.save(price);
		return Optional.of(price);
	}

	// Получение всех цен в PriceDto
	@Override
	public Set<PriceDto> getItemPriciesDto(Set<Price> pricies) {

		pricies.removeIf(price -> price.getActive() == false);
		Set<PriceDto> priciesDto = pricies.stream()
				.map(price -> getItemPriceDto(price.getId()).get()).collect(Collectors.toSet());
		return priciesDto;
	}


	// Получение цены в PriceDto
	private Optional<PriceDto> getItemPriceDto(Long priceId) {

		Price price = findById(priceId).get();
		return Optional.of(PriceDto.fromUser(price));
	}

	// Проверка есть ли у Item такая цена
	public String getCostInCurrency(Set<Price> prices, String currencyTitle) {
		Price price = prices.stream().filter(itemPrice -> currencyTitle.equals(itemPrice.getCurrencyTitle()))
			.findAny().orElse(null);
		
		if (price == null) {
			System.err.println("Данный Item нельзя приобрести за эту валюту"); // TODO: искл
		}
		return price.getCost();
	}

	// Получение Price из PriceRequest
	private Optional<Price> getItemPriceFromRequest(PriceRequest priceRequest) {

		Price price = new Price();
		price.setCost(priceRequest.getCost());
		price.setCurrencyTitle(priceRequest.getCurrency());
		if  (!validatePriceRequest(priceRequest)) {
			price.setActive(false);
		}
		price.setActive(priceRequest.getActive());
		return Optional.of(price);
	}

	// Валидация PriceRequest
	private boolean validatePriceRequest(PriceRequest priceRequest) {

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

		int i = Integer.parseInt(cost);
		if (i < 0 ) {
			return false;
		}
		return true;
	}

	// Сохранение Price
	private Optional<Price> savePrice(Price newPrice) {
	
		Price price = priceRepository.save(newPrice);
		return Optional.of(price);
	}

	private Price findPriceById(Long priceId) {
		return priceRepository.findById(priceId).orElseThrow(null);
	}

	private Optional<Price> findById (Long id) {
		return priceRepository.findById(id);
	}
	
}
