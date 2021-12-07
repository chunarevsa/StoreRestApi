package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.InventoryUnit;
import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.UserInventory;
import com.chunarevsa.Website.Entity.UserItem;
import com.chunarevsa.Website.dto.UserInventoryDto;
import com.chunarevsa.Website.repo.InventoryUnitRepository;
import com.chunarevsa.Website.repo.UserInventoryRepository;
import com.chunarevsa.Website.repo.UserItemRepository;

import org.springframework.stereotype.Service;

@Service
public class UserInventoryService {

	private final UserInventoryRepository userInventoryRepository;
	private final UserItemRepository userItemRepository;
	private final InventoryUnitRepository inventoryUnitRepository;

	public UserInventoryService(UserItemRepository userItemRepository,
				UserInventoryRepository userInventoryRepository,
				InventoryUnitRepository inventoryUnitRepository) {
		this.userInventoryRepository = userInventoryRepository;
		this.userItemRepository = userItemRepository;
		this.inventoryUnitRepository = inventoryUnitRepository;
	}

	/**
	 * Добавление пользователю UserItem
	 */
	public Set<InventoryUnit> addUserItem(UserInventory userInventory, Item item, String amountItems) {
		Set<InventoryUnit> inventoryUnits = userInventory.getInventoryUnit();

		Set<UserItem> userItems = inventoryUnits.stream().map(unit -> unit.getUserItem()).collect(Collectors.toSet());
		UserItem userItem = userItems.stream().filter(
			userItems1 -> Long.toString(item.getId()).equals(userItems1.getItemId()))
			.findAny().orElse(null);
			
		// Проверка есть ли у пользователя ячейка с таким UserItem
		if (userItem == null) {
			// Создание новой ячейки 
			System.err.println("Такого item у вас ещё нет. Создаём новую ячейку");
			InventoryUnit newInventoryUnit = new InventoryUnit();
			newInventoryUnit.setAmountItems(amountItems);

			// Создание новой копии Item
			UserItem newUserItem = new UserItem();
			System.out.println("UserItem newUserItem = new UserItem()");
			newUserItem.setItemId(Long.toString(item.getId()));
			newUserItem.setName(item.getName());
			newUserItem.setType(item.getType());
			newUserItem.setDescription(item.getDescription());
			newUserItem.setActive(item.getActive());
			UserItem savedUserItem = userItemRepository.save(newUserItem);
			newInventoryUnit.setUserItem(savedUserItem);
			InventoryUnit savedInventoryUnit = inventoryUnitRepository.save(newInventoryUnit);
			inventoryUnits.add(savedInventoryUnit);

		} else {
			// Добавление количества UserItem
			InventoryUnit inventoryUnit = inventoryUnits.stream()
				.filter(unit -> userItem.equals(unit.getUserItem())).findAny().orElse(null);
			
			// Изменение количество UserItem в ячейке
			int oldAmountItems =  Integer.parseInt(inventoryUnit.getAmountItems());
			int add = Integer.parseInt(amountItems);
			int newAmountItems =  oldAmountItems + add;

			inventoryUnit.setAmountItems(Integer.toString(newAmountItems));
			inventoryUnits.add(inventoryUnit);
			
		}

			userInventory.setInventoryUnit(inventoryUnits);
			userInventoryRepository.save(userInventory);
			return inventoryUnits;

	}

	/**
	 * Получение инвенторя своего инвенторя в UserInventoryDto c
	 * Ячейками в InventoryUnitDto с UserItemDto
	 */
	public Optional<UserInventoryDto> getUserInventory(User user) {
		
		return Optional.of(UserInventoryDto.fromUser(user.getUserInventory()));
	}

	
	
}
