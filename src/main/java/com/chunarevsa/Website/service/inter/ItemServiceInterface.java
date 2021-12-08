package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.dto.InventoryUnitDto;
import com.chunarevsa.Website.entity.Item;
import com.chunarevsa.Website.entity.Price;
import com.chunarevsa.Website.payload.ItemRequest;
import com.chunarevsa.Website.payload.PriceRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;

import org.springframework.data.domain.Pageable;

public interface ItemServiceInterface {

	/**
	 * Получение Items 
	 * Если ADMIN -> page Items, если USER -> set ItemsDto
	 */
	public Object getItems(Pageable pageable, JwtUser jwtUser);

	/**
	 * Получение Item
	 * Если ADMIN -> Item, если USER -> ItemDto
	 */
	public Object getItem(Long itemId, JwtUser jwtUser);

	/**
	 * Получение у Item списка всех Price
	 * Если ADMIN -> set Pricies, если USER -> set PriciesDto
	 */
	public Object getItemPricies (Long itemId, JwtUser jwtUser);

	/**
	 * Покупка UsetItem (копии Item) за внутреннюю валюту
	 */
	public Set<InventoryUnitDto> buyItem(Long itemId, String amountItems, String currencyTitle, JwtUser jwtUser);
	
	/**
	 * Добавление Item
	 */
	public Optional<Item> addItem (ItemRequest itemRequest);

	/**
	 * Изменение и удаление (выключение) Price 
	 */
	public Optional<Item> editItem (long id, ItemRequest itemRequest);

	/**
	 * Изменение и удаление (выключение) Price 
	 */
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId);

	/**
	 * Удаление (Выключение) Item
	 */
	public void deleteItem(long itemId);

} 
