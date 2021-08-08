package com.chunarevsa.Website.dto.Item;

import com.chunarevsa.Website.Entity.Items;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemsRepository;
import org.springframework.http.HttpStatus;

public class ItemValidator implements ItemService {

	// Проверка на наличие 
	public void itemIsPresent (long id, ItemsRepository itemsRepository) throws NotFound{
		Boolean item1 = itemsRepository.findById(id).isPresent();
		if (item1 == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	// Проверка не выключен ли active = true
	public void activeValidate (long id, Items item) throws NotFound {
		if (item.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
	}

	// Проверка на формат числа
	public void costValidate (Items newItem) throws InvalidPriceFormat {
		int i = Integer.parseInt(newItem.getCost());
		if (i < 0) {
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			}
	}

	// Проверка на незаполеннные данные
	public void bodyIsNotEmpty (Items newItem) throws FormIsEmpty {
		if (newItem.getName().isEmpty() == true || newItem.getSku().isEmpty() == true || 
		newItem.getType().isEmpty() == true || newItem.getDescription().isEmpty() == true || 
		newItem.getCost().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}

	// Представление Id в JSON
	public IdByJson getIdByJson (Items newItem, ItemsRepository itemsRepository) {
		itemsRepository.save(newItem);
		IdByJson idByJson = new IdByJson(newItem.getId());
		return idByJson;
	}

	// Запись параметров
	public Items overrideItem (long id, Items editItem, ItemsRepository itemsRepository) {
		Items item = itemsRepository.findById(id).orElseThrow();
		item.setSku(editItem.getSku());
		item.setName(editItem.getName());
		item.setType(editItem.getType());
		item.setDescription(editItem.getDescription());
		item.setCost(editItem.getCost());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		item.setActive(editItem.getActive());
		return item;
	}
	
	
}
