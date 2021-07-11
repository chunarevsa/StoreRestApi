package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Merchs;
import com.chunarevsa.Website.repo.MerchsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MerchsControllerGUI {
	
	@Autowired
	private MerchsRepository merchsRepository;
	public MerchsControllerGUI (MerchsRepository merchsRepository) {
		this.merchsRepository = merchsRepository;
	}

	// Список мерча
 	@GetMapping ("/merchs/gui")
 	public String merchsMainGUI (Model model) {
		 Iterable<Merchs> merchs = merchsRepository.findAll();
		 model.addAttribute("merchs", merchs); 
		 return "merch-main";
 	}

	// Добавление мерча
	@GetMapping ("/merchs/gui/add")
 	public String merchAddGUI(Model model) {
		 return "merch-add";
 	} 
	@PostMapping ("/merchs/gui/add")
	public String merchsPostAddGUI( @RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
		Merchs merch = new Merchs(sku, name, type, description, cost);
		merchsRepository.save(merch);
		return "redirect:/merchs/gui";
	}

	// Обработчки Динамической ссылки
	@GetMapping ("/merchs/gui/{id}") 
 	public String merchsDetailsGUI (@PathVariable(value = "id") long id, Model model) {
		  if (!merchsRepository.existsById(id)){ 
			return "redirect:/merchs/gui";
		  } 
		  Optional<Merchs> merch = merchsRepository.findById(id);
		  ArrayList<Merchs> res = new ArrayList<>();
		  merch.ifPresent(res::add);
		  model.addAttribute("merch", res);
		  return "merch-details";
 	}

	// Изменение
	 @GetMapping ("/merchs/gui/{id}/edit") 
 	public String gamesEditGUI (@PathVariable(value = "id") long id, Model model) {
		  if (!merchsRepository.existsById(id)){ 
			return "redirect:/merchs/gui";
		  } 
		  Optional<Merchs> merch = merchsRepository.findById(id);
		  ArrayList<Merchs> res = new ArrayList<>();
		  merch.ifPresent(res::add);
		  model.addAttribute("merch", res);
		  return "merchs-edit";
 	}
	@PostMapping ("/merchs/gui/{id}/edit") 
	public String merchsPostUpdateGUI (@PathVariable(value = "id") long id, @RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
		Merchs merch = merchsRepository.findById(id).orElseThrow();
		merch.setType(type);
		merch.setSku(sku);
		merch.setName(name);
		merch.setDescription(description);
		merch.setCost(cost);
		merchsRepository.save(merch);
		return "redirect:/merchs/gui";
	}
	
	// Удаление 
	@PostMapping ("/merchs/gui/{id}/remove") 
	public String merchsPostDelete (@PathVariable(value = "id") long id, Model model) {
		Merchs merch = merchsRepository.findById(id).orElseThrow();
		merchsRepository.delete(merch);
		return "redirect:/merchs/gui";
	}
}
