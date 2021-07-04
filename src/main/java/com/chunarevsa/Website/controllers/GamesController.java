package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // добавляем, чтобы указать, что это контроллер
public class GamesController {

	@Autowired
	private GamesRepository gamesRepository;

 	@GetMapping ("/games")
 	public String gamesMain (Model model) {
		 Iterable<Games> games = gamesRepository.findAll();
		 model.addAttribute("games", games); // Массив данных из таблицы
		 return "games-main";

 	}

	@GetMapping ("/games/add") // Добавление игры
 	public String gamesAdd (Model model) { 		 
		  return "games-add";

 	} 
	@PostMapping ("/games/add") // Добавление игры
	public String gamesPostAdd (@RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		Games game = new Games (name, description, cost);
		game.setType("Игра");
		gamesRepository.save(game);
		return "redirect:/games";
	}
 }