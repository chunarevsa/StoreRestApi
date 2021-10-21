package com.chunarevsa.Website.service;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Status;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.inter.ItemServiceInterface;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ItemService implements ItemServiceInterface {

	private final ItemRepository itemRepository;
	private final ItemValid itemValid;
	private final PriceService priceService;

	public ItemService(
				ItemRepository itemRepository,
				ItemValid itemValid,
				PriceService priceService) {
		this.itemValid = itemValid;
		this.itemRepository = itemRepository;
		this.priceService = priceService;
	}

	// Создание 
	@Override
	public Item addItem(Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat {

		// Проверка на наличие валюты в репе
		priceService.saveAllPrice(bodyItem);
		// Проверка на незаполеннные данные
		if (!itemValid.bodyIsEmpty(bodyItem)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
		
		// Включение (active = true) 
		bodyItem.setStatus(Status.ACTIVE);

		Item item = itemRepository.save(bodyItem);
		log.info("IN addItem - item: {} seccesfully add", item);

		return item;

	}

	// Получение однго итема
	@Override
	public Item getItem (Long id) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!itemValid.itemIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		log.info("IN getItem - {} item is found", itemRepository.findById(id).orElseThrow());
		return itemRepository.findById(id).orElseThrow();
	}

	// Получение модели
	@Override
	public ItemDto getItemModel(Long id) throws NotFound {
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		return ItemDto.toModel(itemRepository.findById(id).get());
	}

	// Перезапись параметров
	@Override
	public Item overridItem (long id, Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat {
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
		item.setStatus(bodyItem.getStatus());
		item.setPrices(bodyItem.getPrices());

		// Запись параметров
		itemRepository.save(item);
		log.info("IN overridItem - {} item is found", item);
		return item;
	}

	// Удаление
	@Override
	public void deleteItem(long id) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!itemValid.itemIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		Item item = itemRepository.findById(id).orElseThrow();
		item.setStatus(Status.DELETED);
		itemRepository.save(item);
		log.info("IN delete - item with id: {} successfully deleted");

	}	

	// Вывод Id в JSON
	@Override
	public IdDto getIdByJson (Item bodyItem) {
		itemRepository.save(bodyItem);
		return new IdDto(bodyItem.getId());
	}
	
}
