package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemServiceInterface {

	// Получение страницы со всеми Item  
	public Page<Item> getPageItemFromAdmin(Pageable pageable);

	// Получение всех цен у айтема (влючая выкленные)
	public Set<Price> getItemPriciesFromAdmin(Long itemId);

	// Получение списка всех ItemDto
	public Set<ItemDto> getItemsDtoFromUser();

	// Получение списка всех PriceDto у конкретного Item
	public Set<PriceDto> getItemPriciesFromUser(Long itemId);

	// Добавление Item
	public Optional<Item> addItem (ItemRequest itemRequest);

	// Изменение Item (без цен)
	public Optional<Item> editItem (long id, ItemRequest itemRequest);

	// Изменение и удаление цены
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId);

	// Удаление (Выключение) Item
	public Optional<Item> deleteItem(long itemId);

	// Получение ItemDto
	public ItemDto getItemDto(Long id);

} 
