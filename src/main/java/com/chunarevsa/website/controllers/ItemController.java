package com.chunarevsa.website.controllers;

import javax.validation.Valid;

import com.chunarevsa.website.payload.ApiResponse;
import com.chunarevsa.website.payload.EditItemRequest;
import com.chunarevsa.website.payload.ItemRequest;
import com.chunarevsa.website.payload.PriceRequest;
import com.chunarevsa.website.security.jwt.JwtUser;
import com.chunarevsa.website.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/item")
@Api(value = "Item Rest API", description = "Товар, который можно приобрести за внутреннюю валюту")
public class ItemController {

	private final ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * Получение Items
	 * Если ADMIN -> page Items, если USER -> set ItemsDto
	 * @param pageable
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity getItems(@ApiIgnore Pageable pageable,
		@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(itemService.getItems(pageable, jwtUser));
	}

	/**
	 * Получение Item
	 * Если ADMIN -> Item, если USER -> ItemDto
	 * @param id
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение Item. Формат ответа зависить от роли")
	public ResponseEntity getItem(@PathVariable(value = "id") Long id,
			@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(itemService.getItem(id, jwtUser));
	}

	/**
	 * Получение у Item списка всех Price
	 * Если ADMIN -> set prices, если USER -> set pricesDto
	 * @param itemId
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/{id}/prices")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение у Item списка всех Price. Формат ответа зависить от роли")
	public ResponseEntity getItemprices(@PathVariable(value = "id") Long itemId,
			@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(itemService.getItemprices(itemId, jwtUser));
	}

	/**
	 * Покупка Item за внутреннюю валюту
	 * @param itemId
	 * @param amountitem
	 * @param currencytitle
	 * @param jwtUser
	 * @return
	 */
	@PostMapping("/{id}/buy")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Покупка Item за внутреннюю валюту")
	public ResponseEntity buyItem(@PathVariable(value = "id") Long itemId,
			@RequestParam String amountitem,
			@RequestParam String currencytitle,
			@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(itemService.buyItem(itemId, amountitem, currencytitle, jwtUser));
	}

	/**
	 * Добавление Item
	 * @param itemRequest
	 * @return
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Добавление Item")
	public ResponseEntity addItem(@Valid @RequestBody ItemRequest itemRequest) {

		return ResponseEntity.ok().body(itemService.addItem(itemRequest));
	}

	/**
	 * Изменение Item (без цен)
	 * @param id
	 * @param editItemRequest
	 * @return
	 */
	@PutMapping("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Изменение Item (без цен)")
	public ResponseEntity editItem(@PathVariable(value = "id") Long id,
			@Valid @RequestBody EditItemRequest editItemRequest) {

		return ResponseEntity.ok().body(itemService.editItem(id, editItemRequest));
	}

	/**
	 * Добавление цены Item
	 * @param itemId
	 * @param priceRequest
	 * @return
	 */
	@PostMapping("/{id}/prices/add")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Добавление цены Item")
	public ResponseEntity addItemPrice(@PathVariable(value = "id") Long itemId,
			@Valid @RequestBody PriceRequest priceRequest) {

		return ResponseEntity.ok().body(itemService.addItemPrice(priceRequest, itemId));
	}

	/**
	 * Изменение и удаление (выключение) Price
	 * @param priceId
	 * @param priceRequest
	 * @return
	 */
	@PutMapping("/price/{priceId}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity editItemPrice(@PathVariable(value = "priceId") Long priceId,
			@Valid @RequestBody PriceRequest priceRequest) {

		return ResponseEntity.ok().body(itemService.editItemPrice(priceRequest, priceId));
	}

	/**
	 *  Удаление (Выключение) Item
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
	public ResponseEntity deleteItem(@PathVariable(value = "id") Long id) {
		itemService.deleteItem(id);
		return ResponseEntity.ok(new ApiResponse(true, "Item " + id + " был удален"));
	}

}
