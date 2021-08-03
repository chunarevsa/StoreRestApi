package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Items;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.dto.Id;
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
		Boolean item1 = itemsRepository.findById(id).isPresent();
		if (!item1 == true) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}  
		Items item = itemsRepository.findById(id).orElseThrow();
		// Вывести только в случае active = true
		if (item.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		return item;
	} 

	// Добавление 
	@PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Id createdItem (@RequestBody Items newItems) throws AllException {
		int i;
		// Проверка на формат числа
		try {
			i = Integer.parseInt(newItems.getCost());
			if (i < 0) {
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
		}	
		// Проверка на незаполеннные данные
		if (newItems.getName().isEmpty() == true || 
		newItems.getSku().isEmpty() == true || newItems.getType().isEmpty() == true || 
		newItems.getDescription().isEmpty() == true || newItems.getCost().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
		newItems.setActive(true);
		itemsRepository.save(newItems);
		Id id = new Id(newItems.getId());
		return id;
	} 	
				
	 // Изменение
	@PutMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Items editItem (@PathVariable(value = "id") long id, @RequestBody Items editItems) throws AllException {
		// Проверка на наличие
		Boolean itemBoolean = itemsRepository.findById(id).isPresent();
		if (!itemBoolean == true) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		Items item = itemsRepository.findById(id).orElseThrow();
		item.setSku(editItems.getSku());
		item.setName(editItems.getName());
		item.setType(editItems.getType());
		item.setDescription(editItems.getDescription());
		item.setCost(editItems.getCost());
		int i;
		// Проверка на формат числа
		try {
			i = Integer.parseInt(editItems.getCost());
			if (i < 0) {
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
		}
		// Проверка на незаполеннные данные
		if (editItems.getName().isEmpty() == true || editItems.getSku().isEmpty() == true ||
		   editItems.getType().isEmpty() == true || editItems.getDescription().isEmpty() == true || 
			editItems.getCost().isEmpty() == true) {
				throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		item.setActive(editItems.getActive());
		itemsRepository.save(item);
		return item;
	} 

   // Удаление
	@DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteItem (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие Item
		Boolean item1 = itemsRepository.findById(id).isPresent();
		if (!item1 == true) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		Items item = itemsRepository.findById(id).orElseThrow();
		if (item.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		item.setActive(false);
		itemsRepository.save(item);
		Response response = new Response("Успешное удаленние", HttpStatus.OK);
		return response;

	}

}
