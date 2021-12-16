package com.chunarevsa.website1.service;

import java.util.Set;

import com.chunarevsa.website1.dto.UserInventoryDto;
import com.chunarevsa.website1.entity1.InventoryUnit;
import com.chunarevsa.website1.entity1.Item;
import com.chunarevsa.website1.entity1.User;
import com.chunarevsa.website1.entity1.UserInventory;
import com.chunarevsa.website1.repo.InventoryUnitRepository;
import com.chunarevsa.website1.repo.UserInventoryRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserInventoryService {
	
	private static final Logger logger = LogManager.getLogger(UserInventoryService.class);

	private final UserInventoryRepository userInventoryRepository;
	private final InventoryUnitRepository inventoryUnitRepository;

	public UserInventoryService(
					UserInventoryRepository userInventoryRepository,
					InventoryUnitRepository inventoryUnitRepository) {
		this.userInventoryRepository = userInventoryRepository;
		this.inventoryUnitRepository = inventoryUnitRepository;
	}

	/**
	 * Добавление пользователю Item
	 */
	public Set<InventoryUnit> addItem(UserInventory userInventory, Item item1, String amountItems) {

		Set<InventoryUnit> inventoryUnits = userInventory.getInventoryUnit();
		InventoryUnit inventoryUnit = inventoryUnits.stream().filter(unit -> item1.equals(unit.getItem())).findAny().orElse(null);
			
		// Проверка есть ли у пользователя ячейка с таким UserItem
		if (inventoryUnit == null) {
			// Создание новой ячейки 
			InventoryUnit newInventoryUnit = new InventoryUnit();
			newInventoryUnit.setAmountItems(amountItems);
			newInventoryUnit.setItem(item1);

			InventoryUnit savedInventoryUnit = inventoryUnitRepository.save(newInventoryUnit);
			inventoryUnits.add(savedInventoryUnit);
			logger.info("Добавлена новая ячейка инвенторя "+ savedInventoryUnit.getId());

		} else {
			// Изменение количество UserItem в ячейке
			int oldAmountItems =  Integer.parseInt(inventoryUnit.getAmountItems());
			int add = Integer.parseInt(amountItems);
			int newAmountItems =  oldAmountItems + add;

			inventoryUnit.setAmountItems(Integer.toString(newAmountItems));
			inventoryUnits.add(inventoryUnit);
			logger.info("В ячейке " + inventoryUnit.getId() + " изменено количество Item :" + newAmountItems);
		}
			userInventory.setInventoryUnit(inventoryUnits);
			userInventoryRepository.save(userInventory);
			return inventoryUnits;
	}

	/**
	 * Получение инвенторя своего инвенторя в UserInventoryDto c
	 * Ячейками в InventoryUnitDto с UserItemDto
	 */
	public UserInventoryDto getUserInventory(User user) {
		return UserInventoryDto.fromUser(user.getUserInventory());
	}

}
