package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.entity.DomesticCurrency;
import com.chunarevsa.Website.entity.Price;
import com.chunarevsa.Website.exception.AlreadyUseException;
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
				.map(priceRequest -> getItemPriceFromRequest(priceRequest))
				.collect(Collectors.toSet());
		return pricies;
	}

	/**
	 * Изменение Price
	 */
	@Override
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {

		Price price = findPriceById(priceId);
		Set<Price> itemPrices = price.getItem().getPrices();

		System.err.println("equals :" + priceRequest.getCurrency().equals(price.getDomesticCurrency().getTitle()));
		if (!priceRequest.getCurrency().equals(price.getDomesticCurrency().getTitle())) {
			System.err.println("if");
			Price pr = itemPrices.stream()
				.filter(itemPrice -> priceRequest.getCurrency().equals(itemPrice.getDomesticCurrency().getTitle()))
				.findAny().orElse(null);
			if (pr  != null) {
				System.err.println("Цена в этой валюте уже есть ");
				logger.error("В Item " + price.getItem().getId() + " есть цена в " + priceRequest.getCurrency());
				throw new AlreadyUseException("Price in this Item", "currency",  priceRequest.getCurrency());
			}
		}

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency());
		Price newPrice = getItemPriceFromRequest(priceRequest);

		price.setCost(newPrice.getCost());
		price.setDomesticCurrency(domesticCurrency);
		price.setActive(newPrice.getActive());
		Price savedPrice = savePrice(price);
		logger.info("Цена " + priceId + " была изменена");
		return Optional.of(savedPrice);
	}

	/**
	 * Удаление списка Price
	 */
	@Override
	public Set<Price> deletePricies (Set<Price> prices) { 
		return prices.stream()
				.map(price -> deletePrice(price.getId())).collect(Collectors.toSet());
	}

	/**
	 * Удаление Price
	 */
	private Price deletePrice (Long priceId) {

		Price price = findPriceById(priceId);
		price.setActive(false);
		savePrice(price);
		return price;
	}

	/**
	 * Получение всех цен в PriceDto
	 */
	@Override
	public Set<PriceDto> getItemPriciesDto(Set<Price> pricies) {

		Set<Price> activePricies = pricies.stream().filter(price -> price.getActive() == true)
				.collect(Collectors.toSet());
		
		if (activePricies == null) {
			logger.error("Нет активных Item");
			throw new ResourceNotFoundException("Price", "active", true);
		}

		Set<PriceDto> priciesDto = activePricies.stream()
				.map(activePrice -> getItemPriceDto(activePrice.getId())).collect(Collectors.toSet());
		
		return priciesDto;
	}

	/**
	 * Получение цены в PriceDto
	 */
	private PriceDto getItemPriceDto(Long priceId) {
		Price price = findPriceById(priceId);
		if (!price.isActive()){
			logger.error("Цена  " + priceId + " не активна");
			throw new ResourceNotFoundException("Price", "active", true);
		}
		return PriceDto.fromUser(price);
	}

	/**
	 * Получение у Item цены в конкретной валюте
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
	private Price getItemPriceFromRequest(PriceRequest priceRequest) {

		DomesticCurrency domesticCurrency = domesticCurrencyService.findCurrencyByTitile(priceRequest.getCurrency());
		validatePriceRequest(priceRequest);

		Price price = new Price();
		price.setCost(priceRequest.getCost());
		price.setDomesticCurrency(domesticCurrency);
		price.setActive(priceRequest.getActive());
		return price;
	} 

	/**
	 * Валидация PriceRequest
	 */
	private boolean validatePriceRequest(PriceRequest priceRequest) {

		if (!validateCostInPriceRequest(priceRequest.getCost())) {
			logger.error("Сумма " + priceRequest.getCost() + " заполнена не верно");
			throw new InvalidAmountFormat("Стоимость", "price", (priceRequest.getCost()));
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
		try {
			int i = Integer.parseInt(cost); 
		if (i < 0 ) {
			return false;
		}
		return true;

		} catch (NumberFormatException e) {
			throw new InvalidAmountFormat("Сумма", "cost", cost);
		}
	}

	/**
	 * Сохранение списка Price
	 */
	@Override
	public Set<Price> savePricies(Set<Price> pricies) {
		return pricies.stream().map(price -> savePrice(price)).collect(Collectors.toSet());
	}

	/**
	 * Сохранение Price
	 */
	private Price savePrice(Price price) {
		return priceRepository.save(price);
	}

	public Price findPriceById(Long priceId) {
		return priceRepository.findById(priceId)
			.orElseThrow(() -> new ResourceNotFoundException("Price", "id", priceId));
	}
	
}
