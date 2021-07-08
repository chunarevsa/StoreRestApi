package com.chunarevsa.Website.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.controllers.ExampleRestController.RestResponse;
import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController // добавляем, чтобы указать, что это контроллер

public class GamesController {

	@Autowired
	
	private GamesRepository gamesRepository;
	public GamesController (GamesRepository gamesRepository) {
		this.gamesRepository = gamesRepository;
	}
	
	// Оригинал /games 
	/* @GetMapping ("/games")
 	public String gamesMain (Model model) {
		 Iterable<Games> games = gamesRepository.findAll();
		 model.addAttribute("games", games); // Массив данных из таблицы
		 return "games-main";
 	} */

	// Вариант 2 Рабочий c одной игрой
	/* @RequestMapping (path = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Games gamesMethod () { // входящий параметр для этого метода
		Games games = new Games();
		games.setName("Первый нах");
		return games;
	}  */

	// Варинат 3 с выводом массива
	@RequestMapping (path = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Games> gamesMethod () { 
		Iterable<Games> games = gamesRepository.findAll();
		return games;
	} 
	 
	// Добавление игры 
	// Оригинал /games/add
	/* @GetMapping ("/games/add") 
 	public String gamesAdd (Model model) { 		 
		  return "games-add";
 	}  */

	/* @PostMapping ("/games/add") 
	public String gamesPostAdd (@RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		Games game = new Games (name, description, cost);
		game.setType("Игра");
		gamesRepository.save(game);
		return "redirect:/games";
	} */

	// Вариант 1 через body
	/* @PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)
	public Games createdGame (@RequestBody Games newGame) {
		Games game = new Games (newGame);
		newGame.setName("name");
		newGame.setDescription("description");
		newGame.setType("type");
		newGame.setCost(5);
   	return this.gamesRepository.save(newGame);
	} */

	@PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)
	public Games createdGame (@RequestBody Games newGame) {
   	return this.gamesRepository.;
	}

	// Обработчки Динамической ссылки
	@GetMapping ("/games/{id}") 
 	public String gamesDetails (@PathVariable(value = "id") long id, Model model) {
		  if (!gamesRepository.existsById(id)){ 
			return "redirect:/games";
		  } 
		  Optional<Games> game = gamesRepository.findById(id);
		  ArrayList<Games> res = new ArrayList<>();
		  game.ifPresent(res::add);
		  model.addAttribute("game", res);
		  return "games-details";
 	}

	 // Изменение
	 @GetMapping ("/games/{id}/edit") 
 	public String gamesEdit (@PathVariable(value = "id") long id, Model model) {
		  if (!gamesRepository.existsById(id)){ 
			return "redirect:/games";
		  } 
		  Optional<Games> game = gamesRepository.findById(id);
		  ArrayList<Games> res = new ArrayList<>();
		  game.ifPresent(res::add);
		  model.addAttribute("game", res);
		  return "games-edit";
 	}
	@PostMapping ("/games/{id}/edit") 
	public String gamesPostUpdate (@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		Games game = gamesRepository.findById(id).orElseThrow();
		game.setName(name);
		game.setDescription(description);
		game.setCost(cost);
		gamesRepository.save(game);
		return "redirect:/games";
	} 

   // Удаление 
	@PostMapping ("/games/{id}/remove") 
	public String gamesPostDelete (@PathVariable(value = "id") long id, Model model) {
		Games game = gamesRepository.findById(id).orElseThrow();
		gamesRepository.delete(game);
		return "redirect:/games";
	}
 }