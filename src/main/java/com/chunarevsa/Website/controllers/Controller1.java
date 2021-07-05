package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.models.Merchs;
import com.chunarevsa.Website.repo.GamesRepository;
import com.chunarevsa.Website.repo.MerchsRepository;
import com.chunarevsa.Website.repo.СurrenciesRepository;

import org.springframework.beans.factory.annotation.Autowired;

// копируем с https://spring.io/guides/gs/serving-web-content/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

	@Autowired
	private GamesRepository gamesRepository;

	@Autowired
	private MerchsRepository merchsRepository;

	@Autowired
	private СurrenciesRepository currenciesRepository;

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

	@PostMapping ("/") 
	public String searchId(@RequestParam long id, Model model) {
		
		 if (gamesRepository.existsById(id)) { 
		  Optional<Games> game = gamesRepository.findById(id);
		  ArrayList<Games> res = new ArrayList<>();
		  game.ifPresent(res::add);
		  model.addAttribute("game", res);
		  return "games-details";
		  } 
		
		  if (merchsRepository.existsById(id)) { 
			Optional<Merchs> merch = merchsRepository.findById(id);
			ArrayList<Merchs> res = new ArrayList<>();
			merch.ifPresent(res::add);
			model.addAttribute("merch", res);
			return "merch-details";
			} 
		
		  if (currenciesRepository.existsById(id)) { 
		  Optional<Currencies> currency = currenciesRepository.findById(id);
		  ArrayList<Currencies> res = new ArrayList<>();
		  currency.ifPresent(res::add);
		  model.addAttribute("currency", res);
		  return "currencies-details";
		  }  
		  return "redirect:/";  
		
		 
	} 
}