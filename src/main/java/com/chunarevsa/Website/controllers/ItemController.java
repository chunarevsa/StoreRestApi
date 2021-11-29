package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.ItemService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	private final ItemService itemService;
	
	public ItemController (ItemService itemService) {
			this.itemService = itemService;
	}

	// Получение списка всех item с ограничением страницы (10)
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public Page<Item> getAll (@PageableDefault Pageable pageable) { 
		return itemService.getPage(pageable);
	} 

	// Получение по id
	@RequestMapping ("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getOneItem (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemDto(id));
	} 

	// Добавление 
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity createdItem (@Valid @RequestBody ItemRequest itemRequest) throws AllException {
		
		return itemService.addItem(itemRequest)
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
