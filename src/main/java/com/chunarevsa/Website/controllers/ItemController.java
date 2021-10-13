package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.ItemModel;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.ItemService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	private final ItemRepository itemRepository;	
	private final ItemService itemService;
	
	public ItemController (
				ItemRepository itemRepository, 
				ItemService itemService, 
				PriceRepository priceRepository,
				CurrencyRepository currencyRepository) {
			this.itemRepository = itemRepository;
			this.itemService = itemService;
	}

	// Получение списка всех Items с ограничением страницы (10)
	@RequestMapping (path = "/item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Item> findAllItem (@PageableDefault(sort = { "active"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Item> pageItems = itemRepository.findByActive(true, pageable);
		return pageItems;
	}

	// Получение по id
	@RequestMapping (path = "/item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemModel getOneItem (@PathVariable(value = "id") Long id) throws AllException { 
		itemService.getItem(id);
		return itemService.getItemModel(id);
	} 

	// Добавление 
	@PostMapping (value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Item createdItem (@RequestBody Item bodyItem) throws AllException {
		return itemService.addItem(bodyItem);
	} 	
				
	 // Изменение
	@PutMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Item editItem (@PathVariable(value = "id") long id, @RequestBody Item bodyItem) throws AllException {
		return itemService.overridItem(id, bodyItem);
	} 

   // Выключение
	@DeleteMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteItem(@PathVariable(value = "id") long id) throws AllException {
		return ResponseEntity.ok().body(itemService.deleteItem(id));
	}

}
