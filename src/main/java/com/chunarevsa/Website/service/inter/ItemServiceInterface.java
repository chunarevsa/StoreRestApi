package com.chunarevsa.Website.service.inter;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.dto.ItemDto;

public interface ItemServiceInterface {
	
	// Создание 
	public Item addItem(Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat, NotFoundDomesticCurrency;

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
