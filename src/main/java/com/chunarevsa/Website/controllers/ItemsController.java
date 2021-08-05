package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Items;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.dto.Response;
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
	public ItemsController (ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
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
		itemIsPresent(id);
		Items item = itemsRepository.findById(id).orElseThrow();
		// Выводим только в случае active = true 
		activeValidate(item.getId(), item);
		return item;
	} 

	// Добавление 
	@PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public IdByJson createdItem (@RequestBody Items newItem) throws AllException {
		// Проверка на формат числа
		costValidate(newItem);
		// Проверка на незаполеннные данные
		bodyIsNotEmpty(newItem);
		// Включение (active = true) 
		newItem.setActive(true);
		// Представление Id в JSON
		return getIdByJson(newItem);
	} 	
				
	 // Изменение
	@PutMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Items editItem (@PathVariable(value = "id") long id, @RequestBody Items editItem) throws AllException {
		// Проверка на наличие
		itemIsPresent(id);
		// Проверка на формат числа
		costValidate(editItem);
		// Проверка на незаполеннные данные
		bodyIsNotEmpty(editItem);
		// Запись параметров
		Items item = overrideItem(id, editItem);
		itemsRepository.save(item);
		return item;
	} 

   // Удаление
	@DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteItem (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие Item
		itemIsPresent(id);
		Items item = itemsRepository.findById(id).orElseThrow();
		// Проверка не выключен ли active = true
		activeValidate(item.getId(), item);
		// Выключение active = false
		item.setActive(false);
		itemsRepository.save(item);
		// Вывод об успешном удалении
		Response response = new Response(item, item.getActive());
		return response;
	}
	

	// Проверка на наличие 
	public void itemIsPresent (long id) throws NotFound {
		Boolean item1 = itemsRepository.findById(id).isPresent();
		if (item1 == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	// Проверка не выключен ли active = true
	public void activeValidate (long id, Items item) throws NotFound {
		if (item.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
	}

	// Проверка на формат числа
	public void costValidate (Items newItem) throws InvalidPriceFormat {
		int i = Integer.parseInt(newItem.getCost());
		if (i < 0) {
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			}
	}

	// Проверка на незаполеннные данные
	public void bodyIsNotEmpty (Items newItem) throws FormIsEmpty {
		if (newItem.getName().isEmpty() == true || newItem.getSku().isEmpty() == true || 
		newItem.getType().isEmpty() == true || newItem.getDescription().isEmpty() == true || 
		newItem.getCost().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}

	// Представление Id в JSON
	public IdByJson getIdByJson (Items newItem) {
		itemsRepository.save(newItem);
		IdByJson idByJson = new IdByJson(newItem.getId());
		return idByJson;
	}

	// Запись параметров
	public Items overrideItem (long id, Items editItem) {
		Items item = itemsRepository.findById(id).orElseThrow();
		item.setSku(editItem.getSku());
		item.setName(editItem.getName());
		item.setType(editItem.getType());
		item.setDescription(editItem.getDescription());
		item.setCost(editItem.getCost());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		item.setActive(editItem.getActive());
		return item;
	}


}
