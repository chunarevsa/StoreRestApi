package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.entity.Price;
import com.chunarevsa.Website.payload.PriceRequest;

public interface PriceServiceInterface {

	/**
	* Получение списка Price из списка PriceRequest
	*/
	public Set<Price> getItemPriciesFromRequest (Set<PriceRequest> setPriciesRequests);

	/**
	 * Сохранение списка Price
	 */
	public Set<Price> savePricies(Set<Price> pricies);

	/**
	 * Валидация на наличие у Item цены в валюте из PriceRequest
	 */
	public Price getValidatedPrice(Set<Price> pricies, PriceRequest priceRequest);

	/**
	 * Изменение Price
	 */
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId);

	/**
	 * Удаление списка Price
	 */
	public Set<Price> deletePricies (Set<Price> prices);

	/**
	 * Получение всех цен в PriceDto
	 */
	public Set<PriceDto> getItemPriciesDto(Set<Price> pricies);
	
}
