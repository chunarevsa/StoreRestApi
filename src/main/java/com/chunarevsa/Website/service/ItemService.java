package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.inter.ItemServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemServiceInterface {

	private final ItemRepository itemRepository;
	private final PriceService priceService;

	@Autowired
	public ItemService(
				ItemRepository itemRepository,
				PriceService priceService) {
		this.itemRepository = itemRepository;
		this.priceService = priceService;
	}

	// Получение страницы со всеми Item
	@Override
	public Page<Item> getPageItemFromAdmin(Pageable pageable) {
		return findAllItem(pageable);

	}

	// Получение всех цен у айтема (влючая выкленные)
	@Override
	public Set<Price> getItemPriciesFromAdmin(Long itemId) {
		return priceService.getItemPricies(itemId);
	}

	// Получение списка всех ItemDto
	@Override
	public Set<ItemDto> getItemsDtoFromUser() {
		Set<Item> items = findAllByActive(true);
		Set<ItemDto> itemsDto = items.stream().
				map(item -> getItemDto(item.getId())).collect(Collectors.toSet());
		return itemsDto;
	}

	// Получение списка всех PriceDto у конкретного Item
	@Override
	public Set<PriceDto> getItemPriciesFromUser(Long itemId) {
		return priceService.getItemPriciesDto(itemId);
	}

	// Добавление Item
	@Override
	public Optional<Item> addItem (ItemRequest itemRequest) {
		System.out.println("addItem");
		Item newItem = new Item();
		newItem.setName(itemRequest.getName());
		newItem.setDescription(itemRequest.getDescription());
		newItem.setType(itemRequest.getType());
		newItem.setActive(itemRequest.getActive());
		Set<Price> pricies = priceService.getItemPriciesFromRequest(itemRequest.getPricies());

		newItem.setPrices(pricies);
		priceService.savePricies(newItem.getPrices());
		Item item = saveItem(newItem).get();
		return Optional.of(item);

	}

	// Изменение Item (без цен)
	@Override
	public Optional<Item> editItem (long id, ItemRequest itemRequest)  {

		Item item = itemRepository.findById(id).orElseThrow(); // mb get())
		item.setName(itemRequest.getName());
		item.setType(itemRequest.getType());
		item.setDescription(itemRequest.getDescription());
		item.setActive(itemRequest.isActive());
		saveItem(item);
		return Optional.of(item);
	}

	// Изменение и удаление цены
	@Override
	public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId) {
		return priceService.editPrice(priceRequest, priceId);
	}

	// Удаление (Выключение) Item
	@Override
	public Optional<Item> deleteItem(long itemId) {

		Item item = itemRepository.findById(itemId).orElseThrow();
		priceService.deletePricies(item.getPrices());
		item.setActive(false);
		saveItem(item);
		return Optional.of(item);

	}

	// Получение ItemDto
	@Override
	public ItemDto getItemDto(Long id) {
		Item item = findById(id).get();
		return ItemDto.fromUser(item);
	}

	private Set<Item> findAllByActive(boolean active) {
		return itemRepository.findAllByActive(active);
	} 

	private Page<Item> findAllItem(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	private Optional<Item> findById (Long id) {
		return itemRepository.findById(id);
	}

	private Optional<Item> saveItem(Item item) {
		System.out.println("saveItem");
		return Optional.of(itemRepository.save(item));
	}

}
