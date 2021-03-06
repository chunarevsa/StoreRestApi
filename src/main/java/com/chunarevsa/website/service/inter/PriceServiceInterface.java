package com.chunarevsa.website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.website.dto.PriceDto;
import com.chunarevsa.website.entity.Price;
import com.chunarevsa.website.payload.PriceRequest;

public interface PriceServiceInterface {

	/**
	 * Получение списка Price из списка PriceRequest
	 */
	public Set<Price> getItempricesFromRequest(Set<PriceRequest> setpricesRequests);

	/**
	 * Сохранение списка Price
	 */
	public Set<Price> saveprices(Set<Price> prices);

	/**
	 * Валидация на наличие у Item цены в валюте из PriceRequest
	 */
	public Price getValidatedPrice(Set<Price> prices, PriceRequest priceRequest);

	/**
	 * Изменение Price
	 */
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId);

	/**
	 * Удаление списка Price
	 */
	public Set<Price> deleteprices(Set<Price> prices);

	/**
	 * Получение всех цен в PriceDto
	 */
	public Set<PriceDto> getItempricesDto(Set<Price> prices);

}
