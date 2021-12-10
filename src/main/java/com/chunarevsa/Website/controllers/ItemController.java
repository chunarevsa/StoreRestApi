package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.payload.ApiResponse;
import com.chunarevsa.Website.payload.ItemRequest;
import com.chunarevsa.Website.payload.PriceRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Товар который можно приобрести за внутреннюю валюту
 * Например: броня, стрелы, скин... что угодно. 
 */
@RestController
@RequestMapping("/item")
@Api(value = "Item Rest API", description = "Товар который можно приобрести за внутреннюю валюту")
public class ItemController {

	private final ItemService itemService;
	
	@Autowired
	public ItemController (ItemService itemService) {
			this.itemService = itemService;
	}

	/**
	 * Получение Items 
	 * Если ADMIN -> page Items, если USER -> set ItemsDto
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity getItems(@PageableDefault Pageable pageable, 
				@AuthenticationPrincipal JwtUser jwtUser) { 

		return ResponseEntity.ok().body(itemService.getItems(pageable, jwtUser));
	} 

	/**
	 * Получение Item
	 * Если ADMIN -> Item, если USER -> ItemDto
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение Item. Формат ответа зависить от роли")
	public ResponseEntity getItem (@PathVariable(value = "id") Long id, 
			@AuthenticationPrincipal JwtUser jwtUser)  {

		return ResponseEntity.ok().body(itemService.getItem(id, jwtUser));
	}

	/**
	 * Получение у Item списка всех Price
	 * Если ADMIN -> set Pricies, если USER -> set PriciesDto
	 * @param itemId
	 * @param jwtUser
	 */
	@GetMapping("/{id}/pricies")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение у Item списка всех Price. Формат ответа зависить от роли")
	public ResponseEntity getItemPricies (@PathVariable(value = "id") Long itemId, 
					@AuthenticationPrincipal JwtUser jwtUser)  {
	
		return ResponseEntity.ok().body(itemService.getItemPricies(itemId, jwtUser));
	}

	/**
	 * Покупка Item за внутреннюю валюту
	 * @param itemId
	 * @param amountitem
	 * @param currencytitle
	 * @param jwtUser
	 */
	@PostMapping("/{id}/buy")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Покупка Item за внутреннюю валюту")
	public ResponseEntity buyItem (@PathVariable(value = "id") Long itemId,
					@RequestParam String amountitem,
					@RequestParam String currencytitle,
					@AuthenticationPrincipal JwtUser jwtUser) {
		
		return ResponseEntity.ok().body(itemService.buyItem(itemId, amountitem, currencytitle, jwtUser));
	} 

	/**
	 * Добавление Item
	 * @param itemRequest
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Добавление Item") // TODO: AllExceptional
	public ResponseEntity addItem (@Valid @RequestBody ItemRequest itemRequest) {

		return ResponseEntity.ok().body(itemService.addItem(itemRequest));
	} 	
	// TODO: ответы ApiResponse сделать одинаково во всех контр
	 /**
	  * Изменение Item (без цен)
	  * @param id
	  * @param itemRequest
	  */
	@PutMapping("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Изменение Item (без цен)") // TODO: AllExceptional
	public ResponseEntity editItem (@PathVariable(value = "id") Long id, 
				@Valid @RequestBody ItemRequest itemRequest) {

		return ResponseEntity.ok(itemService.editItem(id, itemRequest)); 
	}
	
	// Добавление цены item
	// При покупке проврка цены на активность




	/**
	 * Изменение и удаление (выключение) Price 
	 */
	@PutMapping("/price/{priceId}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity editItemPrice (@PathVariable(value = "priceId") Long priceId,
				@Valid @RequestBody PriceRequest priceRequest)  {
		
		return ResponseEntity.ok().body(itemService.editItemPrice(priceRequest, priceId));
	} 

	/**
	 * Удаление (Выключение) Item
	 * @param id
	 */
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity deleteItem (@PathVariable(value = "id") Long id)  {
		itemService.deleteItem(id);
		return ResponseEntity.ok(new ApiResponse(true, "Item " + id + " был удален"));
	}

}
