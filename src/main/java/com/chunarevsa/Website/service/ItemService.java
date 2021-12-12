package com.chunarevsa.Website.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.dto.InventoryUnitDto;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.entity.Item;
import com.chunarevsa.Website.entity.Price;
import com.chunarevsa.Website.exception.InvalidAmountFormat;
import com.chunarevsa.Website.exception.ResourceNotFoundException;
import com.chunarevsa.Website.payload.EditItemRequest;
import com.chunarevsa.Website.payload.ItemRequest;
import com.chunarevsa.Website.payload.PriceRequest;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.ItemServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemServiceInterface {

	private static final Logger logger = LogManager.getLogger(ItemService.class);

	private final ItemRepository itemRepository;
	private final PriceService priceService;
	private final UserService userService;

	@Autowired
	public ItemService(
			ItemRepository itemRepository,
			PriceService priceService,
			UserService userService) {
		this.itemRepository = itemRepository;
		this.priceService = priceService;
		this.userService = userService;
	}

	/**
	 * Получение Items
	 * Если ADMIN -> page Items, если USER -> set ItemsDto
	 */
	@Override
	public Object getItems(Pageable pageable, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return getPageItemFromAdmin(pageable);
		}
		return getItemsDtoFromUser();
	}

	/**
	 * Получение Item
	 * Если ADMIN -> Item, если USER -> ItemDto
	 */
	@Override
	public Object getItem(Long itemId, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			return findById(itemId);
		}
		Item item = findById(itemId);
		if (!item.isActive()) {
			logger.error("Item  " + itemId + " не активен");
			throw new ResourceNotFoundException("Price", "active", true);
		}
		return getItemDto(itemId);
	}

	/**
	 * Получение у Item списка всех Price
	 * Если ADMIN -> set prices, если USER -> set pricesDto
	 */
	@Override
	public Object getItemprices(Long itemId, JwtUser jwtUser) {
		List<String> roles = jwtUser.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		if (roles.contains("ROLE_ADMIN")) {
			return getItempricesFromAdmin(itemId);
		}
		return getItempricesFromUser(itemId);
	}

	/**
	 * Покупка UsetItem (копии Item) за внутреннюю валюту
	 */
	@Override
	public Set<InventoryUnitDto> buyItem(Long itemId, String amountItems, String currencyTitle, JwtUser jwtUser) {

		Item item = findById(itemId);

		if (!validateAmountItems(amountItems)) {
			logger.error("Неверный формат суммы Items :" + amountItems);
			throw new InvalidAmountFormat("Сумма", "Item", amountItems);
		}

		String cost = priceService.getCostInCurrency(item.getPrices(), currencyTitle);
		return userService.getSavedInventoryUnit(jwtUser, currencyTitle, cost, amountItems, item);
	}

	/**
	 * Добавление Item
	 */
	@Override
	public Optional<Item> addItem(ItemRequest itemRequest) {

		Set<Price> prices = priceService.getItempricesFromRequest(itemRequest.getprices());

		Item newItem = new Item();
		newItem.setName(itemRequest.getName());
		newItem.setDescription(itemRequest.getDescription());
		newItem.setType(itemRequest.getType());
		newItem.setActive(itemRequest.getActive());
		newItem.setPrices(prices);
		priceService.saveprices(newItem.getPrices());
		Item savedItem = saveItem(newItem);
		logger.info("Создан новый Item :" + savedItem.getName());
		return Optional.of(savedItem);
	}

	/**
	 * Изменение Item (без цен)
	 */
	@Override
	public Optional<Item> editItem(long id, EditItemRequest editItemRequest) {

		Item item = findById(id);
		item.setName(editItemRequest.getName());
		item.setType(editItemRequest.getType());
		item.setDescription(editItemRequest.getDescription());
		item.setActive(editItemRequest.isActive());
		saveItem(item);
		logger.info("Item " + item.getName() + " был изменен");
		return Optional.of(item);
	}

	/**
	 * Добавление новой цены с валидацией
	 */
	@Override
	public Optional<Item> addItemPrice(PriceRequest priceRequest, Long itemId) {

		Item item = findById(itemId);
		Price newPrice = priceService.getValidatedPrice(item.getPrices(), priceRequest);
		item.getPrices().add(newPrice); 
		priceService.saveprices(item.getPrices());
		Item savedItem = saveItem(item);
		logger.info("Добавлена новая цена для " + savedItem.getName());
		return Optional.of(savedItem);
	}

	/**
	 * Изменение и удаление (выключение) Price
	 */
	@Override
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId) {
		return priceService.editPrice(priceRequest, priceId);
	}

	/**
	 * Удаление (Выключение) Item
	 */
	@Override
	public void deleteItem(long itemId) {

		Item item = findById(itemId);
		priceService.deleteprices(item.getPrices());
		item.setActive(false);
		saveItem(item);
		logger.info("Item " + itemId + " был выключен");
	}

	/**
	 * Получение страницы со всеми Item
	 */
	private Page<Item> getPageItemFromAdmin(Pageable pageable) {
		return findAllItem(pageable);
	}

	/**
	 * Получение всех Price у айтема (влючая выкленные)
	 */
	private Set<Price> getItempricesFromAdmin(Long itemId) {
		Item item = findById(itemId);
		return item.getPrices();
	}

	/**
	 * Получение списка всех ItemDto
	 */
	private Set<ItemDto> getItemsDtoFromUser() {
		Set<Item> items = findAllByActive(true);
		if (items.isEmpty()) {
			logger.info("Нет активных Item");
			throw new ResourceNotFoundException("Item", "active", true);
		}

		Set<ItemDto> itemsDto = items.stream().map(item -> getItemDto(item.getId())).collect(Collectors.toSet());
		return itemsDto;
	}

	/**
	 * Получение списка всех PriceDto у конкретного Item
	 */
	private Set<PriceDto> getItempricesFromUser(Long itemId) {
		Item item = findById(itemId);
		return priceService.getItempricesDto(item.getPrices());
	}

	/**
	 * Получение ItemDto
	 */
	private ItemDto getItemDto(Long id) {
		return ItemDto.fromUser(findById(id));
	}

	private Set<Item> findAllByActive(boolean active) {
		Set<Item> activeItems = itemRepository.findAllByActive(active);
		if (activeItems == null) {
			logger.error("Нет активных Item");
			throw new ResourceNotFoundException("Items", "active", true);
		}
		return activeItems;
	}

	private Page<Item> findAllItem(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	private Item findById(Long id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
	}

	public Set<Item> saveItems(Set<Item> items) {
		return items.stream().map(item -> saveItem(item)).collect(Collectors.toSet());
	}

	private Item saveItem(Item item) {
		return itemRepository.save(item);
	}

	/**
	 * Проверка суммы
	 */
	private boolean validateAmountItems(String amountItems) {
		try {
			int value = Integer.parseInt(amountItems);
			if (value <= 0) {
				return false;
			} else {
				return true;
			}

		} catch (NumberFormatException e) {
			return false;
		}
	}

}
