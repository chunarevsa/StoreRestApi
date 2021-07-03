package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Merchs;
import com.chunarevsa.Website.repo.MerchsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MerchsController {
	
	@Autowired
	private MerchsRepository merchsRepository;

 	@GetMapping ("/merch")
 	public String merchsgMain (Model model) {
		 Iterable<Merchs> merch = merchsRepository.findAll();
		 model.addAttribute("merch", merch); // Массив данных из таблицы
		 return "merch-main";

 	}
}
