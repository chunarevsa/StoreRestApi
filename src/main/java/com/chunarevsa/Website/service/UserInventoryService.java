package com.chunarevsa.Website.service;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.dto.UserInventoryDto;
import com.chunarevsa.Website.entity.InventoryUnit;
import com.chunarevsa.Website.entity.Item;
import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.UserInventory;
import com.chunarevsa.Website.repo.InventoryUnitRepository;
import com.chunarevsa.Website.repo.UserInventoryRepository;

import org.springframework.stereotype.Service;

@Service
public class UserInventoryService {

	private final UserInventoryRepository userInventoryRepository;
	private final InventoryUnitRepository inventoryUnitRepository;

	public UserInventoryService(
				UserInventoryRepository userInventoryRepository,
				InventoryUnitRepository inventoryUnitRepository) {
	this.userInventoryRepository = userInventoryRepository;
		this.inventoryUnitRepository = inventoryUnitRepository;
	}

	/**
	 * Добавление пользователю UserItem
	 */
	public Set<InventoryUnit> addUserItem(UserInventory userInventory, Item item1, String amountItems) {
		Set<InventoryUnit> inventoryUnits = userInventory.getInventoryUnit();

		InventoryUnit inventoryUnit = inventoryUnits.stream().filter(unit -> item1.equals(unit.getItem())).findAny().orElse(null);
			
		// Проверка есть ли у пользователя ячейка с таким UserItem
		if (inventoryUnit == null) {
			// Создание новой ячейки 
			System.err.println("Такого item у вас ещё нет. Создаём новую ячейку");
			InventoryUnit newInventoryUnit = new InventoryUnit();
			newInventoryUnit.setAmountItems(amountItems);
			newInventoryUnit.setItem(item1);

			InventoryUnit savedInventoryUnit = inventoryUnitRepository.save(newInventoryUnit);
			inventoryUnits.add(savedInventoryUnit);

		} else {
			
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
