package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}