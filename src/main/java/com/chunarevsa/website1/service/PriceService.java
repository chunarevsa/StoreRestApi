package com.chunarevsa.website1.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.website1.dto.PriceDto;
import com.chunarevsa.website1.entity1.DomesticCurrency;
import com.chunarevsa.website1.entity1.Price;
import com.chunarevsa.website1.exception1.AlreadyUseException;
import com.chunarevsa.website1.exception1.InvalidAmountFormat;
import com.chunarevsa.website1.exception1.ResourceNotFoundException;
import com.chunarevsa.website1.payload.PriceRequest;
import com.chunarevsa.website1.repo.PriceRepository;
import com.chunarevsa.website1.service.inter.PriceServiceInterface;

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
	public Set<Price> getItempricesFromRequest(Set<PriceRequest> pricesRequest) {

		Set<Price> prices = pricesRequest.stream()
				.map(priceRequest -> getItemPriceFromRequest(priceRequest))
				.collect(Collectors.toSet());
		return prices;
	}

	/**
	 * Изменение Price
	 */
	@Override
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {

		Price price = findPriceById(priceId);
		// Проверка есть ли у Item цена в валюте из PriceRquest
		if (validateCurrencyIsPresentInItem(priceRequest, price)) {
			logger.error("В Item " + price.getItem().getId() + " есть цена в " + priceRequest.getCurrency());
			throw new AlreadyUseException("Price in this Item", "currency", priceRequest.getCurrency());
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
	 * Валидация на наличие у Item цены в валюте из PriceRequest
	 */
	@Override
	public Price getValidatedPrice(Set<Price> prices, PriceRequest priceRequest) {

		Price newItemPrice = getItemPriceFromRequest(priceRequest);
		Price find = prices.stream()
				.filter(itemPrice -> newItemPrice.getDomesticCurrency().getTitle()
						.equals(itemPrice.getDomesticCurrency().getTitle()))
				.findAny().orElse(null);
		if (find != null) {
			logger.error("Цена с такой валютой уже есть у Item");
			throw new AlreadyUseException("Price in this Item", "currency", priceRequest.getCurrency());
		}
		return newItemPrice;
	}

	public boolean validateCurrencyIsPresentInItem(PriceRequest priceRequest, Price price) {
		Set<Price> itemPrices = price.getItem().getPrices();

		if (!priceRequest.getCurrency().equals(price.getDomesticCurrency().getTitle())) {
			Price pr = itemPrices.stream()
					.filter(itemPrice -> priceRequest.getCurrency().equals(itemPrice.getDomesticCurrency().getTitle()))
					.findAny().orElse(null);
			if (pr != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Удаление списка Price
	 */
	@Override
	public Set<Price> deleteprices(Set<Price> prices) {
		return prices.stream()
				.map(price -> deletePrice(price.getId())).collect(Collectors.toSet());
	}

	/**
	 * Удаление Price
	 */
	private Price deletePrice(Long priceId) {

		Price price = findPriceById(priceId);
		price.setActive(false);
		savePrice(price);
		return price;
	}

	/**
	 * Получение всех цен в PriceDto
	 */
	@Override
	public Set<PriceDto> getItempricesDto(Set<Price> prices) {

		Set<Price> activeprices = prices.stream().filter(price -> price.getActive() == true)
				.collect(Collectors.toSet());

		if (activeprices == null) {
			logger.error("Нет активных Item");
			throw new ResourceNotFoundException("Price", "active", true);
		}

		Set<PriceDto> pricesDto = activeprices.stream()
				.map(activePrice -> getItemPriceDto(activePrice.getId())).collect(Collectors.toSet());

		return pricesDto;
	}

	/**
	 * Получение цены в PriceDto
	 */
	private PriceDto getItemPriceDto(Long priceId) {
		Price price = findPriceById(priceId);
		if (!price.isActive()) {
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
		if (!price.isActive()) {
			logger.info("Цена в валюте " + domesticCurrency.getTitle() + " не активна");
			throw new ResourceNotFoundException("Цена", "active", true);
		}

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

		if (!priceRequest.isActive()) {
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
			if (i < 0) {
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
	public Set<Price> saveprices(Set<Price> prices) {
		return prices.stream().map(price -> savePrice(price)).collect(Collectors.toSet());
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
