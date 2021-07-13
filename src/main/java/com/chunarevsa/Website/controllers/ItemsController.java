package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Items;
import com.chunarevsa.Website.repo.ItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {
	
	@Autowired

	private ItemsRepository itemsRepository;
	public ItemsController (ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	// Получение списка всех Items с ограничением страницы (10)
	@RequestMapping (path = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Items> itemsFindAll (@PageableDefault(sort = { "id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		Page<Items> pageGames = itemsRepository.findAll(pageable);
		return pageGames;
	}

	// Получение по id
	@RequestMapping (path = "/items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Items itemsMethod (@PathVariable(value = "id") long id) { 
		Items item = itemsRepository.findById(id).orElseThrow();
		return item;
	} 
	 
	// Добавление 
	@PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public long createdItem (@RequestBody Items newItems) {
		itemsRepository.save(newItems);
		return newItems.getId();
		} 	
				
	 // Изменение
	@PutMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Items editItem (@PathVariable(value = "id") long id, @RequestBody Items editItems) {
		Items item = itemsRepository.findById(id).orElseThrow();
		item.setSku(editItems.getSku());
		item.setName(editItems.getName());
		item.setType(editItems.getType());
		item.setDescription(editItems.getDescription());
		item.setCost(editItems.getCost());
		itemsRepository.save(item);
		return item;
	} 

   // Удаление
	@DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteItem (@PathVariable(value = "id") long id) {
		 Boolean item = itemsRepository.findById(id).isPresent();
		if (!item == true) {
			return "Нет такого олух";
		}  
		itemsRepository.deleteById(id);
		
		return "Удалено"; 
	}


}
