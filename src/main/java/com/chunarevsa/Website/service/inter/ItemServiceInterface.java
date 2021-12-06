package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;

import org.springframework.data.domain.Pageable;

public interface ItemServiceInterface {

	// Получение Items 
	// Если ADMIN -> page Items, если USER -> set ItemsDto
	public Object getItems(Pageable pageable, JwtUser jwtUser);

	// Получение Item
	// Если ADMIN -> Item, если USER -> ItemDto
	public Object getItem(Long itemId, JwtUser jwtUser);

	// Получение у Item списка всех цен
	// Если ADMIN -> set Pricies, если USER -> set PriciesDto
	public Object getItemPricies (Long itemId, JwtUser jwtUser);

	// Добавление Item
	public Optional<Item> addItem (ItemRequest itemRequest);

	// Изменение Item (без цен)
	public Optional<Item> editItem (long id, ItemRequest itemRequest);

	// Удаление (Выключение) Item
	public void deleteItem(long itemId);

	// Изменение и удаление цены
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId);

} 
