package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Items;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.dto.Response;
import com.chunarevsa.Website.dto.Item.ItemValidator;
import com.chunarevsa.Website.repo.ItemsRepository;

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
public class ItemsController {
	
	@Autowired
	private ItemsRepository itemsRepository;	
	@Autowired
	private ItemValidator itemValidator;
	public ItemsController (ItemsRepository itemsRepository, ItemValidator itemValidator) {
		this.itemsRepository = itemsRepository;
		this.itemValidator = itemValidator;
	}

	// Получение списка всех Items с ограничением страницы (10)
	@RequestMapping (path = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Items> itemsFindAll (@PageableDefault(sort = { "active"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Items> pageGames = itemsRepository.findByActive(true, pageable);
		return pageGames;
	}

	// Получение по id
	@RequestMapping (path = "/items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Items itemsMethod (@PathVariable(value = "id") long id) throws AllException { 
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemsRepository);
		Items item = itemsRepository.findById(id).orElseThrow();
		// Выводим только в случае active = true 
		itemValidator.activeValidate(item.getId(), item);
		return item;
	} 

	// Добавление 
	@PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public IdByJson createdItem (@RequestBody Items newItem) throws AllException {
		// Проверка на незаполеннные данные
		itemValidator.bodyIsNotEmpty(newItem);
		// Проверка на формат числа
		itemValidator.costValidate(newItem);
		// Включение (active = true) 
		newItem.setActive(true);
		// Представление Id в JSON
		return itemValidator.getIdByJson(newItem, itemsRepository);
	} 	
				
	 // Изменение
	@PutMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Items editItem (@PathVariable(value = "id") long id, @RequestBody Items editItem) throws AllException {
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemsRepository);
		// Проверка на незаполеннные данные
		itemValidator.bodyIsNotEmpty(editItem);
		// Проверка на формат числа
		itemValidator.costValidate(editItem);
		// Запись параметров
		Items item = itemValidator.overrideItem(id, editItem, itemsRepository);
		itemsRepository.save(item);
		return item;
	} 

   // Удаление
	@DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteItem (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие 
		itemValidator.itemIsPresent(id, itemsRepository);
		Items item = itemsRepository.findById(id).orElseThrow();
		// Проверка не выключен ли active = true
		itemValidator.activeValidate(item.getId(), item);
		// Выключение active = false
		item.setActive(false);
		itemsRepository.save(item);
		// Вывод об успешном удалении
		Response response = new Response(item.getActive());
		return response;
	}

}
