package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;

public interface ItemServiceInterface {
	
	// Создание 
	public Optional<Item> addItem (ItemRequest itemRequest);

	// Получение однго итема
	public Item getItem (Long id) throws NotFound;

	// Получение модели
	public ItemDto getItemModel(Long id) throws NotFound;

	// Перезапись параметров
	public Item overridItem (long id, Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat, NotFoundDomesticCurrency;

	// Удаление
	public void deleteItem(long id) throws NotFound;
	
	// Id в JSON
	public IdDto getIdByJson (Item bodyItem) throws NotFound;
} 
