package com.chunarevsa.Website.service;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.ItemModel;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.valid.CurrencyValid;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.http.HttpStatus;
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

	// Создание 
	public Item addItem(Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat {
		// Проверка на наличие валюты в репе
		priceService.saveAllPrice(bodyItem);
		// Проверка на незаполеннные данные
		itemValid.bodyIsEmpty(bodyItem);
		// Включение (active = true) 
		bodyItem.setActive(true);
		return itemRepository.save(bodyItem);
	}

	// Получение однго итема
	public Item getItem (Long id) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!itemValid.itemIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		return itemRepository.findById(id).orElseThrow();
	}

	// Получение модели
	public ItemModel getItemModel(Long id) {
		return ItemModel.toModel(itemRepository.findById(id).get());
	}

	// Перезапись параметров
	public Item overrideItem (long id, Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		// Проверка на незаполеннные данные
		 if (itemValid.bodyIsEmpty(bodyItem)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}  
		// Проверка цен
		priceService.saveAllPrice(bodyItem);
		Item item = itemRepository.findById(id).orElseThrow();
		item.setSku(bodyItem.getSku());
		item.setName(bodyItem.getName());
		item.setType(bodyItem.getType());
		item.setDescription(bodyItem.getDescription());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		item.setActive(bodyItem.getActive());
		// item.setPrices(bodyItem.getPrices());
		// Запись параметров
		itemRepository.save(item);
		return item;
	}

	// Удаление
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

	// Вывод Id в JSON
	public IdByJson getIdByJson (Item bodyItem) {
		itemRepository.save(bodyItem);
		return new IdByJson(bodyItem.getId());
	}
	
}
