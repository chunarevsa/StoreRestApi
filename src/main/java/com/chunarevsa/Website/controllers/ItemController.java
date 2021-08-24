package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Item;

import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.Response;
import com.chunarevsa.Website.dto.Item.ItemValidator;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.repo.PriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;	
	@Autowired
	private ItemValidator itemValidator;
	@Autowired PriceRepository priceRepository;
	public ItemController 
	(ItemRepository itemRepository, 
	 ItemValidator itemValidator, 
	 PriceRepository priceRepository) {
		this.itemRepository = itemRepository;
		this.itemValidator = itemValidator;
		this.priceRepository = priceRepository;
	}

	// Получение списка всех Items с ограничением страницы (10)
	@RequestMapping (path = "/item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Item> itemsFindAll (@PageableDefault(sort = { "active"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Item> pageGames = itemRepository.findByActive(true, pageable);
		return pageGames;
	}

	// Получение по id
	@RequestMapping (path = "/item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Item itemsMethod (@PathVariable(value = "id") long id) throws AllException { 
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemRepository);
		Item item = itemRepository.findById(id).orElseThrow();
		// Выводим только в случае active = true 
		itemValidator.activeValidate(item.getId(), item);
		return item;
	} 

	// Добавление 
	@PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Item createdItem (@RequestBody Item bodyItem) throws AllException {

		Item item = bodyItem;
		priceRepository.saveAll(item.getPrices());

		// Проверка на незаполеннные данные
		itemValidator.bodyIsNotEmpty(item);
		// Проверка на формат числа
		itemValidator.costValidate(item);
		// Включение (active = true) 
		item.setActive(true);
		// Представление Id в JSON
		// itemValidator.getIdByJson(bodyItem, itemRepository);
		itemRepository.save(item);
		return item;
		// return itemValidator.getIdByJson(bodyItem, itemRepository);
	} 	
				
	 // Изменение
	@PutMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Item editItem (@PathVariable(value = "id") long id, @RequestBody Item bodyItem) throws AllException {
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemRepository);
		// Проверка на незаполеннные данные
		itemValidator.bodyIsNotEmpty(bodyItem);
		// Проверка на формат числа
		itemValidator.costValidate(bodyItem);
		// Запись параметров
		Item item = itemValidator.overrideItem(id, bodyItem, itemRepository);
		itemRepository.save(item);
		return item;
	} 

   // Удаление
	@DeleteMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteItem (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemRepository);
		Item item = itemRepository.findById(id).orElseThrow();
		// Проверка не выключен ли active = true
		itemValidator.activeValidate(item.getId(), item);
		// Выключение active = false
		item.setActive(false);
		itemRepository.save(item);
		// Вывод об успешном удалении
		Response response = new Response(item.getActive());
		return response;
	}

}
