package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

	// Получение страницы со всеми Item  
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Item> getPageItemFromAdmin (@PageableDefault Pageable pageable) { 
		return itemService.getPageItemFromAdmin(pageable);
	} 

	// Получение всех цен у айтема (влючая выкленные)
	@GetMapping("/{id}/pricies/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity getItemPriciesFromAdmin (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemPriciesFromAdmin(id));
	}

	// Получение списка всех ItemDto
	@GetMapping 
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getItemsDtoFromUser () { 
		return ResponseEntity.ok().body(itemService.getItemsDtoFromUser());
	}

	// Получение списка всех PriceDto у конкретного Item
	@GetMapping("/{id}/pricies")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getItemPriciesFromUser (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemPriciesFromUser(id));
	}
	
	// Получение ItemDto
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getItemDto (@PathVariable(value = "id") Long id) throws AllException {
	
		return ResponseEntity.ok().body(itemService.getItemDto(id));
	}

	// Добавление Item
	@PostMapping 
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity addItem (@Valid @RequestBody ItemRequest itemRequest) throws AllException {

		return itemService.addItem(itemRequest) // TODO: исключение
				.map(item -> ResponseEntity.ok().body("Item добавлен")).orElseThrow();
	} 	
	
	 // Изменение Item (без цен)
	@PutMapping("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editItem (@PathVariable(value = "id") Long id, 
				@Valid @RequestBody ItemRequest itemRequest) throws AllException {
		
		return ResponseEntity.ok(itemService.editItem(id, itemRequest)); 
	}

	// Изменение и удаление цены
	@PutMapping("/price/edit/{priceId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editItemPrice (@PathVariable(value = "priceId") Long priceId,
				@Valid @RequestBody PriceRequest priceRequest) throws AllException {
		return ResponseEntity.ok().body(itemService.editItemPrice(priceRequest, priceId));
	} 
 
   // Удаление (Выключение) Item
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteItem (@PathVariable(value = "id") Long id) throws AllException {
		itemService.deleteItem(id);
		return ResponseEntity.ok().body("Item" + id + " был удален");
	}

}
