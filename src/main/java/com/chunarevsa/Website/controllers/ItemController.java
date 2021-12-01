package com.chunarevsa.Website.controllers;

import java.util.Set;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	private final ItemService itemService;
	
	@Autowired
	public ItemController (ItemService itemService) {
			this.itemService = itemService;
	}


	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Item> getPageItemFromAdmin (@PageableDefault Pageable pageable) { 
		return itemService.getPageItemFromAdmin(pageable);
	} 

	@GetMapping ("/{id}/pricies/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity getItemPriciesFromAdmin (@PathVariable(value = "id") Long id,
				@PageableDefault Pageable pageable) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemPriciesFromAdmin(id, pageable));
	}

	// Получение списка всех item с ограничением страницы (10)
	@GetMapping 
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getItemFromUser () { 
		return ResponseEntity.ok().body(itemService.getItemsDtoFromUser());
	}

	@GetMapping ("/{id}/pricies")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getItemPriciesFromUser (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemPriciesFromUser(id));
	}
	
	// Получение по id
	@GetMapping ("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getOneItem (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemDto(id));
	}

	// Добавление 
	@PostMapping 
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity createdItem (@Valid @RequestBody ItemRequest itemRequest) throws AllException {
		System.out.println("itemRequest is :" + itemRequest);
		System.out.println("itemRequest.getPricies() is :" + itemRequest.getPricies());
		return itemService.addItem(itemRequest)
				.map(item -> ResponseEntity.ok().body("Item добавлен")).orElseThrow();
	} 	
				
	 // Изменение
	@PutMapping ("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editItem (@PathVariable(value = "id") Long id, 
				@Valid @RequestBody ItemRequest itemRequest) throws AllException {
		
		return ResponseEntity.ok(itemService.editItem(id, itemRequest)); 
	}

	@PutMapping ("/price/edit/{priceId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editItemPrice (@PathVariable(value = "priceId") Long priceId,
				@Valid @RequestBody PriceRequest priceRequest) throws AllException {
		return ResponseEntity.ok().body(itemService.editPrice(priceRequest, priceId));
	} 

 
   // Удаление (Выключение)
	@DeleteMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteItem (@PathVariable(value = "id") Long id) throws AllException {
		return ResponseEntity.ok().body(id);
	}

	// Добавление цены

	// Удаление цены

}
