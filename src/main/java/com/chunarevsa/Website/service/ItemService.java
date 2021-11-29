package com.chunarevsa.Website.service;

import java.util.Optional;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.inter.ItemServiceInterface;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
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

	public Page<Item> getPage(Pageable pageable) {
		Page<Item> page = findAllByActive(true, pageable);
		return page;
	}

	private Page<Item> findAllByActive(boolean active, Pageable pageable) {
		return itemRepository.findAllByActive(active, pageable);
	}

	public ItemDto getItemDto(Long id) {
		Item item = findById(id).get();
		return ItemDto.fromUser(item);
	}

	private Optional<Item> findById (Long id) {
		return itemRepository.findById(id);
	}

	// Создание 
	@Override
	public Optional<Item> addItem (ItemRequest itemRequest) {

		Item item = new Item();
		item.setName(itemRequest.getName());
		item.setType(itemRequest.getDescription());
		item.setActive(itemRequest.getActive());
		priceService.saveAllPrice(itemRequest.getPricies(), item);
		saveItem(item);
		return Optional.of(item);

	}

	private Item saveItem(Item item) {
		return itemRepository.save(item);
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

		return itemRepository.findById(id).orElseThrow();
	}

	// Получение модели
	public ItemDto getItemModel(Long id) throws NotFound {
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		return ItemDto.fromUser(itemRepository.findById(id).orElseThrow());
	}

	// Перезапись параметров
	@Override
	public Item overridItem (long id, Item bodyItem) throws NotFound, FormIsEmpty, InvalidPriceFormat, NotFoundDomesticCurrency {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		// Проверка на незаполеннные данные
		 if (itemValid.bodyIsEmpty(bodyItem)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}  
		// Проверка цен
		//priceService.saveAllPrice(itemRequest.getPricies(), item);

		Item item = itemRepository.findById(id).orElseThrow();
		item.setName(bodyItem.getName());
		item.setType(bodyItem.getType());
		item.setDescription(bodyItem.getDescription());
		// Возможность вернуть удалённый 
		item.setActive(bodyItem.isActive());
		item.setPrices(bodyItem.getPrices());

		// Запись параметров
		itemRepository.save(item);
		return item;
	}

	// Удаление
	@Override
	public void deleteItem(long id) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Удаляем только в случае active 
		if (!itemValid.itemIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		Item item = itemRepository.findById(id).orElseThrow();
		item.setActive(false);
		itemRepository.save(item);
	}	

	//  Id в JSON
	@Override
	public IdDto getIdByJson (Item bodyItem) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(bodyItem.getId())) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		itemRepository.save(bodyItem);
		
		return new IdDto(bodyItem.getId());
	}

	
}
