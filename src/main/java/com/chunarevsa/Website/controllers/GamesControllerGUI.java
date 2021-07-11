package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
public class GamesControllerGUI {

	@Autowired
	private GamesRepository gamesRepository;
	public GamesControllerGUI (GamesRepository gamesRepository) {
		this.gamesRepository = gamesRepository;
	} 

	// Показать весь список игр
	 @GetMapping ("/games/gui")
 	public String gamesMainGUI (Model model) {
		Iterable <Games> games = gamesRepository.findAll();
		 model.addAttribute("games", games); 
		 return "games-main";
 	}

	// Добавление игры 
	@GetMapping ("/games/gui/add") 
 	public String gamesAddGUI (Model model) { 		 
		  return "games-add";
 	} 
	@PostMapping ("/games/gui/add") 
	public String gamesPostAddGUI (@RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
		Games game = new Games (sku, name, type, description, cost);
		gamesRepository.save(game);
		return "redirect:/games/gui";
	}

	// Обработчки Динамической ссылки
	@GetMapping ("/games/gui/{id}") 
 	public String gamesDetailsGUI (@PathVariable(value = "id") long id, Model model) {
		  if (!gamesRepository.existsById(id)){ 
			return "redirect:/games/gui";
		  } 
		  Optional<Games> game = gamesRepository.findById(id);
		  ArrayList<Games> res = new ArrayList<>();
		  game.ifPresent(res::add);
		  model.addAttribute("game", res);
		  return "games-details";
 	}

	 // Изменение
	 @GetMapping ("/games/gui/{id}/edit") 
 	public String gamesEditGUI (@PathVariable(value = "id") long id, Model model) {
		  if (!gamesRepository.existsById(id)){ 
			return "redirect:/games/gui";
		  } 
		  Optional<Games> game = gamesRepository.findById(id);
		  ArrayList<Games> res = new ArrayList<>();
		  game.ifPresent(res::add);
		  model.addAttribute("game", res);
		  return "games-edit";
 	}
	@PostMapping ("/games/gui/{id}/edit") 
	public String gamesPostUpdateGUI (@PathVariable(value = "id") long id, @RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
		Games game = gamesRepository.findById(id).orElseThrow();
		game.setType(type);
		game.setSku(sku);
		game.setName(name);
		game.setDescription(description);
		game.setCost(cost);
		gamesRepository.save(game);
		return "redirect:/games/gui";
	} 

   // Удаление 
	@PostMapping ("/games/gui/{id}/remove") 
	public String gamesPostDeleteGUI (@PathVariable(value = "id") long id, Model model) {
		Games game = gamesRepository.findById(id).orElseThrow();
		gamesRepository.delete(game);
		return "redirect:/games/gui";
	}
 }