package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;

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
