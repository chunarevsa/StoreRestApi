package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.ItemRequest;
import com.chunarevsa.Website.dto.PriceDto;
import com.chunarevsa.Website.dto.PriceRequest;
import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.inter.ItemServiceInterface;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemServiceInterface {

	private final ItemRepository itemRepository;
	private final ItemValid itemValid;
	private final PriceService priceService;

	public ItemService(
				ItemRepository itemRepository,
				ItemValid itemValid,
				PriceService priceService) {
		this.itemValid = itemValid;
		this.itemRepository = itemRepository;
		this.priceService = priceService;
	}

	public Page<Item> getPageItemFromAdmin(Pageable pageable) {
		return findAllItem(pageable);
	}

	public Set<Price> getItemPriciesFromAdmin(Long itemId, Pageable pageable) {
		return priceService.getPagePricies(itemId ,pageable);
	}

	public Set<ItemDto> getItemsDtoFromUser() {
		Set<Item> items = findAllByActive(true);
		Set<ItemDto> itemsDto = items.stream().
				map(item -> getItemDto(item.getId())).collect(Collectors.toSet());
		return itemsDto;
	}

	public Set<PriceDto> getItemPriciesFromUser(Long itemId) {
		return priceService.getItemPriciesDto(itemId);
	}

	/* private Page<Item> findAllByActive(boolean active, Pageable pageable) {
		return itemRepository.findAllByActive(active, pageable);
	} */

	public ItemDto getItemDto(Long id) {
		Item item = findById(id).get();
		return ItemDto.fromUser(item);
	}

	private Optional<Item> findById (Long id) {
		return itemRepository.findById(id);
	}

	// Создание 
	@Override
	public Optional<Item> addItem (ItemRequest itemRequest) {
		System.out.println("addItem");
		Item newItem = new Item();
		newItem.setName(itemRequest.getName());
		newItem.setDescription(itemRequest.getDescription());
		newItem.setType(itemRequest.getType());
		newItem.setActive(itemRequest.getActive());
		Set<Price> pricies = priceService.getPriciesFromRequest(itemRequest.getPricies());

		newItem.setPrices(pricies);
		priceService.savePricies(newItem);
		Item item = saveItem(newItem);
		return Optional.of(item);

	}

	private Item saveItem(Item item) {
		System.out.println("saveItem");
		return itemRepository.save(item);
	}

	public Optional<Price> editPrice(PriceRequest priceRequest, Long priceId) {
		return priceService.editPrice(priceRequest, priceId);
	}

	// Получение однго итема
	@Override
	public Item getItem (Long id) throws NotFound {
		return itemRepository.findById(id).orElseThrow();
	}

	// Получение модели
	public ItemDto getItemModel(Long id) throws NotFound {
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		return ItemDto.fromUser(itemRepository.findById(id).orElseThrow());
	}

	// Перезапись параметров
	@Override
	public Optional<Item> editItem (long id, ItemRequest itemRequest)  {

		Item item = itemRepository.findById(id).orElseThrow();
		item.setName(itemRequest.getName());
		item.setType(itemRequest.getType());
		item.setDescription(itemRequest.getDescription());
		item.setActive(itemRequest.isActive());
		return Optional.of(saveItem(item));
	}

	// Удаление
	@Override
	public void deleteItem(long id) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Удаляем только в случае active 
		if (!itemValid.itemIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		Item item = itemRepository.findById(id).orElseThrow();
		item.setActive(false);
		itemRepository.save(item);
	}	

	//  Id в JSON
	@Override
	public IdDto getIdByJson (Item bodyItem) throws NotFound {
		// Проверка на наличие 
		if (!itemValid.itemIsPresent(bodyItem.getId())) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		itemRepository.save(bodyItem);
		
		return new IdDto(bodyItem.getId());
	}

	private Page<Item> findAllItem(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	private Set<Item> findAllByActive(boolean active) {
		return itemRepository.findAllByActive(active);
	}

}
