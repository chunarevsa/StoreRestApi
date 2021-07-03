package com.chunarevsa.Website.controllers;


import com.chunarevsa.Website.models.Products;
import com.chunarevsa.Website.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;

// import java.util.ArrayList;
// import java.util.Optional;
// import com.chunarevsa.Website.models.Post;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

 

@Controller // добавляем, чтобы указать, что это контроллер
public class GamesController {

	@Autowired
	private ProductsRepository productsRepository;

 	@GetMapping ("/games")
 	public String gamesgMain (Model model) {
		 Iterable<Products> products = productsRepository.findAll();
		 model.addAttribute("products", products); // Массив данных из таблицы
		 return "games-main";

 	}

	

}