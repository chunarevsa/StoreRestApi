package com.chunarevsa.Website.controllers;

// копируем с https://spring.io/guides/gs/serving-web-content/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller // класс который отвечает за обработку всех переходов на сайте
public class Controller1 {

	/* @GetMapping("/")  Здесь указываем какоц URL мы обрабатываем
	 если / - то это главная страничка. 
	 При переходе на главную страницу будет выполняться функция ниже

	public String home (Model model) {  удаляем лишний код
		 Ниже указваем какие данные мы можем передать внутрь шаблона

		model.addAttribute("title", "Главная страница"); 
		 мы передаем параметр title со значеним Главная страница
		
		return "home";  вызываем определнный HTML шаблон
	} */

	@GetMapping("/") 
	public String home (Model model) { 
		model.addAttribute("title", "Главная страница"); 
		return "home"; 
	}

	@GetMapping("/contacts") 
	public String contacts (Model model) { 
		model.addAttribute("title", "Наши контакты"); 
		return "contacts"; 
	}

	 /*  @GetMapping("/blog") 
	public String blog (Model model) { 
		model.addAttribute("title", "Наш блог"); 
		return "blog-main";  
	}   */

	// @GetMapping("/games") 
	// public String games (Model model) { 
	// 	model.addAttribute("title", "Игры"); 
	// 	return "games-main"; 
	// }

}