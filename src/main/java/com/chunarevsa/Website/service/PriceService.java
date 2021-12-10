package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.entity.DomesticCurrency;
import com.chunarevsa.Website.entity.Price;
import com.chunarevsa.Website.exception.InvalidAmountFormat;
import com.chunarevsa.Website.exception.ResourceNotFoundException;
import com.chunarevsa.Website.payload.PriceRequest;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.inter.PriceServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService implements PriceServiceInterface { 

	private static final Logger logger = LogManager.getLogger(PriceService.class);

	private final PriceRepository priceRepository;
	private final DomesticCurrencyService domesticCurrencyService;

	@Autowired
	public PriceService(
				PriceRepository priceRepository,
				DomesticCurrencyService domesticCurrencyService) {
		this.priceRepository = priceRepository;
		this.domesticCurrencyService = domesticCurrencyService;
	}

	/**
	 * Получение списка Price из списка PriceRequest
	 */
	@Override
	public Set<Price> getItemPriciesFromRequest (Set<PriceRequest> priciesRequest) {

		Set<Price> pricies = priciesRequest.stream() 
				.map(priceRequest -> getItemPriceFromRequest(priceRequest).get())
				.collect(Collectors.toSet());
		return pricies;
	}

	/**
	 * Сохранение списка Price
	 */
	@Override
	public Set<Price> savePricies(Set<Price> pricies) {
		return pricies.stream().map(price -> savePrice(price).get()).collect(Collectors.toSet());
	}

	/**
	 * Изменение Price
	 */
	@Override
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency());

		Price price = findPriceById(priceId).get();
		if (price == null) {
			logger.error("Не удалось найти цену " + priceId);
			// TODO: Искл
		}
		Price newPrice = getItemPriceFromRequest(priceRequest).get();
		price.setCost(newPrice.getCost());
		price.setDomesticCurrency(domesticCurrency);
		price.setActive(newPrice.getActive());
		Optional<Price> savedPrice = savePrice(price);
		logger.info("Цена " + priceId + " была изменена");
		return savedPrice;
	}

	/**
	 * Удаление списка Price
	 */
	@Override
	public Set<Price> deletePricies (Set<Price> prices) { 
		return prices.stream()
				.map(price -> deletePrice(price.getId()).get()).collect(Collectors.toSet());
	}

	/**
	 * Удаление Price
	 */
	private Optional<Price> deletePrice (Long id) {

		Price price = priceRepository.findById(id).orElseThrow();
		price.setActive(false);
		priceRepository.save(price);
		return Optional.of(price);
	}

	/**
	 * Получение всех цен в PriceDto
	 */
	@Override
	public Set<PriceDto> getItemPriciesDto(Set<Price> pricies) {

		pricies.removeIf(price -> price.getActive() == false);
		Set<PriceDto> priciesDto = pricies.stream()
				.map(price -> getItemPriceDto(price.getId()).get()).collect(Collectors.toSet());
		return priciesDto;
	}

	/**
	 * Получение цены в PriceDto
	 */
	private Optional<PriceDto> getItemPriceDto(Long priceId) {
		Price price = findById(priceId).get();
		return Optional.of(PriceDto.fromUser(price));
	}


	/**
	 * Проверка есть ли у Item такая цена
	 */
	public String getCostInCurrency(Set<Price> prices, String currencyTitle) {
		
		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(currencyTitle);

		Price price = prices.stream().filter(itemPrice -> domesticCurrency.equals(itemPrice.getDomesticCurrency()))
			.findAny().orElseThrow(() -> new ResourceNotFoundException("Item", "price", currencyTitle));
		
		return price.getCost();
	}

	/**
	 * Получение Price из PriceRequest
	 */
	private Optional<Price> getItemPriceFromRequest(PriceRequest priceRequest) {

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency());

		Price price = new Price();
		price.setCost(priceRequest.getCost());
		price.setDomesticCurrency(domesticCurrency);
		validatePriceRequest(priceRequest);
		price.setActive(priceRequest.getActive());
		return Optional.of(price);
	} 

	/**
	 * Валидация PriceRequest
	 */
	private boolean validatePriceRequest(PriceRequest priceRequest) {

		if (!validateCostInPriceRequest(priceRequest.getCost())) {
			logger.error("Сумма " + priceRequest.getCost() + " заполнена не верно");
			throw new InvalidAmountFormat("Стоимость", "price", (priceRequest.getCost()));
		}

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency());
		if (domesticCurrency == null) {
			logger.error("Валюта" + priceRequest.getCurrency() + " заполнена не верно");
			throw new ResourceNotFoundException("Price", "валюта", priceRequest.getCurrency());
		} 

		if (!priceRequest.isActive()){
			logger.error("Цена " + priceRequest.getCurrency() + " не активна");
			return false;
		}
		return true; 
	}

	/**
	 * Валидация cost
	 */
	private boolean validateCostInPriceRequest(String cost) {

		int i = Integer.parseInt(cost);
		if (i < 0 ) {
			return false;// TODO: валидация
		}
		return true;
	}

	/**
	 * Сохранение Price
	 */
	private Optional<Price> savePrice(Price newPrice) {
		return Optional.of(priceRepository.save(newPrice));
	}

	private Optional<Price> findPriceById(Long priceId) {
		return priceRepository.findById(priceId);
	}

	private Optional<Price> findById (Long id) {
		return priceRepository.findById(id);
	}
	
}
