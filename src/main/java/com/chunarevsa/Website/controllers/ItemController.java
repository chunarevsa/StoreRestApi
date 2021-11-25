package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.ItemService;

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
	/* @RequestMapping (path = "/item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Item> findAllItem (@PageableDefault (sort = { "active"}, direction = Sort.Direction.DESC)  Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Item> pageItems = itemRepository.findByStatus(Status.ACTIVE, pageable);
		return pageItems;
	} */ 

	// Получение по id
	@RequestMapping (path = "/item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getOneItem (@PathVariable(value = "id") Long id) throws AllException { 
		itemService.getItem(id);
		//System.out.println(itemService.getItem(id));

		//System.out.println(itemService.getItemModel(id));
		return ResponseEntity.ok().body(itemService.getItemModel(id));
	} 

	// Добавление 
	@PostMapping (path = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Item createdItem (@RequestBody Item bodyItem) throws AllException {
		return itemService.addItem(bodyItem);
		// Потом переделать на ResponseEntity
	} 	
				
	 // Изменение
	@PutMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Item editItem (@PathVariable(value = "id") Long id, @RequestBody Item bodyItem) throws AllException {
		return itemService.overridItem(id, bodyItem);
	} 

   // Удаление (Выключение)
	@DeleteMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteItem (@PathVariable(value = "id") Long id) throws AllException {
		return ResponseEntity.ok().body(id);
	}

	// Добавление цены

	// Удаление цены

}
