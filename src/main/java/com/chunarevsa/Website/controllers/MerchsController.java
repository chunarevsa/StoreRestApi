package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Merchs;
import com.chunarevsa.Website.repo.MerchsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MerchsController {
	
	@Autowired
	private MerchsRepository merchsRepository;

 	@GetMapping ("/merch")
 	public String merchsMain (Model model) {
		 Iterable<Merchs> merch = merchsRepository.findAll();
		 model.addAttribute("merch", merch); // Массив данных из таблицы
		 return "merch-main";

 	}

	@GetMapping ("/merch/add") // добавление мерча
 	public String merchsAdd(Model model) {
		 return "merch-add";

 	} 

	@PostMapping ("/merch/add")
	public String merchsPostAdd(@RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		Merchs merch = new Merchs(name, description, cost);
		merch.setType("Мерч");
		merchsRepository.save(merch);
		return "redirect:/merch";
	}
	 
}
