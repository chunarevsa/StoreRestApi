package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;

public interface PriceServiceInterface {

	// Получение всех Price
	public Set<Price> getItemPricies(Long itemId);

	// Получение всех цен в PriceDto
	public Set<PriceDto> getItemPriciesDto(Long itemId);

	// Получение цены в PriceDto
	public PriceDto getItemPriceDto(Long id);

	// Получение списка Price из списка PriceRequest
	public Set<Price> getItemPriciesFromRequest (Set<PriceRequest> setPriciesRequests);

	// Изменение цены
	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId);

	// Удаление всех цен
	public Set<Price> deletePricies (Set<Price> prices);

	// Сохранение всех цен
	public Set<Price> savePricies(Set<Price> pricies);






	
}
