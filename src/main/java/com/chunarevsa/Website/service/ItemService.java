package com.chunarevsa.Website.service;


import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.ItemModel;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.valid.CurrencyValid;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.stereotype.Service;

@Service
public class ItemService {

	private final ItemValid itemValid;
	private final ItemRepository itemRepository;
	private final PriceService priceService;

	public ItemService(
				ItemValid itemValid, 
				ItemRepository itemRepository,
				CurrencyValid currencyValid,
				PriceService priceService) {
		this.itemValid = itemValid;
		this.itemRepository = itemRepository;
		this.priceService = priceService;
	}

	// Представление Id в JSON
	public IdByJson getIdByJson (Item bodyItem, ItemRepository itemRepository) {
		itemRepository.save(bodyItem);
		IdByJson idByJson = new IdByJson(bodyItem.getId());
		return idByJson;
	}

	// Запись параметров
	public Item overrideItem (long id, Item bodyItem) throws NotFound, FormIsEmpty {
		// Проверка на наличие 
		itemValid.itemIsPresent(id);
		// Проверка на незаполеннные данные
		itemValid.bodyIsNotEmpty(bodyItem);
		// Проверка на формат числа
		
		Item item = itemRepository.findById(id).orElseThrow();
		item.setSku(bodyItem.getSku());
		item.setName(bodyItem.getName());
		item.setType(bodyItem.getType());
		item.setDescription(bodyItem.getDescription());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		item.setActive(bodyItem.getActive());
		item.setPrices(bodyItem.getPrices());
		// Запись параметров
		itemRepository.save(item);
		return item;
	}

	public Item getItem (Long id) throws NotFound {
		// Проверка на наличие 
		itemValid.itemIsPresent(id);
		// Выводим только в случае active = true 
		itemValid.itemIsActive(id);
		return itemRepository.findById(id).orElseThrow();
	}
	
	public ItemModel getItemModel(Item item) {
		
		return null;
	}

	public Item addItem(Item bodyItem) throws NotFound, FormIsEmpty {
		// Проверка на наличие валюты в репе
		priceService.saveAllPrice(bodyItem);
		// Проверка на незаполеннные данные
		itemValid.bodyIsNotEmpty(bodyItem);
		// Включение (active = true) 
		bodyItem.setActive(true);
		return itemRepository.save(bodyItem);
	}

	public Long deleteItem(long id) throws NotFound {
		// Проверка на наличие 
		itemValid.itemIsPresent(id);
		// Проверка не выключен ли active = true
		itemValid.itemIsActive(id);

		Item item = itemRepository.findById(id).orElseThrow();
		item.setActive(false);
		itemRepository.save(item);
		return id;
	}	
	
}
