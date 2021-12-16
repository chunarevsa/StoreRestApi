package com.chunarevsa.website1.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.website1.dto.InventoryUnitDto;
import com.chunarevsa.website1.entity1.Item;
import com.chunarevsa.website1.entity1.Price;
import com.chunarevsa.website1.payload.EditItemRequest;
import com.chunarevsa.website1.payload.ItemRequest;
import com.chunarevsa.website1.payload.PriceRequest;
import com.chunarevsa.website1.security.jwt.JwtUser;

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
	 * Если ADMIN -> set prices, если USER -> set pricesDto
	 */
	public Object getItemprices(Long itemId, JwtUser jwtUser);

	/**
	 * Покупка UsetItem (копии Item) за внутреннюю валюту
	 */
	public Set<InventoryUnitDto> buyItem(Long itemId, String amountItems, String currencyTitle, JwtUser jwtUser);

	/**
	 * Добавление Item
	 */
	public Optional<Item> addItem(ItemRequest itemRequest);

	/**
	 * Добавление новой цены с валидацией
	 */
	public Optional<Item> addItemPrice(PriceRequest priceRequest, Long itemId);

	/**
	 * Изменение и удаление (выключение) Price
	 */
	public Optional<Item> editItem(long id, EditItemRequest editItemRequest);

	/**
	 * Изменение и удаление (выключение) Price
	 */
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId);

	/**
	 * Удаление (Выключение) Item
	 */
	public void deleteItem(long itemId);

}
