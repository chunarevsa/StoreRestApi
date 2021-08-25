package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.repo.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class ControllerGUI {

	@Autowired
	private ItemRepository itemsRepository;

	// Список всех товаров
	@GetMapping ("/")
 	public String itemsMain (Model model) {
		 Iterable<Item> items = itemsRepository.findAll();
		 model.addAttribute("items", items); 
		 return "home";
 	}

	@GetMapping("/contacts") 
	public String contacts (Model model) { 
		model.addAttribute("title", "Наши контакты"); 
		return "contacts"; 
	}

	// Поиск по ID
	@PostMapping ("/") 
	public String searchId(@RequestParam long id, Model model) {
		  Optional<Item> item = itemsRepository.findById(id);
		  ArrayList<Item> res = new ArrayList<>();
		  item.ifPresent(res::add);
		  model.addAttribute("item", res);
		  return "items-details";
	}

	 // Добавление товара
	 @GetMapping ("/items/gui/add")
	 public String itemsAdd (Model model) {
		 return "items-add";
	 }
	 @PostMapping ("/items/gui/add")
	 public String itemsPostAdd (@RequestParam String sku, @RequestParam String name,@RequestParam String type, @RequestParam String description, @RequestParam String cost, Model model) {
		 Item item = new Item(sku, name, type, description);
		 itemsRepository.save(item);
		 return "redirect:/";
	 }

	 // Обработчки Динамической ссылки
	@GetMapping ("/items/gui/{id}") 
	public String itemsDetails (@PathVariable(value = "id") long id, Model model) {
		 if (!itemsRepository.existsById(id)){ 
		  return "redirect:/";
		 } 
		 Optional<Item> item = itemsRepository.findById(id);
		 ArrayList<Item> res = new ArrayList<>();
		 item.ifPresent(res::add);
		 model.addAttribute("item", res);
		 return "items-details";
	}

	// Изменение
	@GetMapping ("/items/gui/{id}/edit") 
	public String itemEdit (@PathVariable(value = "id") long id, Model model) {
		 if (!itemsRepository.existsById(id)){ 
		  return "redirect:/";
		 } 
		 Optional<Item> item = itemsRepository.findById(id);
		 ArrayList<Item> res = new ArrayList<>();
		 item.ifPresent(res::add);
		 model.addAttribute("item", res);
		 return "items-edit";
	}
  	@PostMapping ("/items/gui/{id}/edit") 
  	public String itemsPostUpdate (@PathVariable(value = "id") long id,@RequestParam String sku, @RequestParam String name,@RequestParam String type, @RequestParam String description, @RequestParam String cost, Model model) {
	  Item item = itemsRepository.findById(id).orElseThrow();
	  item.setName(name);
	  item.setSku(sku);
	  item.setType(type);
     item.setDescription(description);
	  itemsRepository.save(item);
	  return "redirect:/";
  }

  // Удаление 
	@PostMapping ("/items/gui/{id}/remove") 
	public String itemsPostDelete (@PathVariable(value = "id") long id, Model model) {
		Item item = itemsRepository.findById(id).orElseThrow();
		itemsRepository.delete(item);
		return "redirect:/";
	}
} 