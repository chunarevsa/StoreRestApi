package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController // добавляем, чтобы указать, что это контроллер

public class GamesController {

	@Autowired
	
	private GamesRepository gamesRepository;
	public GamesController (GamesRepository gamesRepository) {
		this.gamesRepository = gamesRepository;
	}
	
	@RequestMapping (path = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Games> gamesMethod () { 
		Iterable<Games> games = gamesRepository.findAll();
		return games;
	} 
	 
	// Добавление игры 
	@PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Games createdGame (@RequestBody Games newGames) {		
		return gamesRepository.save(newGames);
		}	

	 // Изменение

	// Оригинал
	/*  @GetMapping ("/games/{id}/edit") 
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
	} */ 

	@PutMapping(value = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Games editGames (@PathVariable(value = "id") long id, @RequestBody Games gameBody)	{

		Games game = gamesRepository.findById(id).orElseThrow();
		game.setSku(gameBody.getSku());
		game.setName(gameBody.getName());
		game.setDescription(gameBody.getDescription());
		game.setCost(gameBody.getCost());
		gamesRepository.save(game);
		return game;
	} 

   // Удаление

	// Оригинал
	/* @ PostMapping ("/games/{id}/remove") 
	public String gamesPostDelete (@PathVariable(value = "id") long id, Model model) {
		Games game = gamesRepository.findById(id).orElseThrow();
		gamesRepository.delete(game);
		return "redirect:/games";
	} */

	@DeleteMapping(value = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Games deleteGames (@PathVariable(value = "id") long id, @RequestBody Games gameBody)	{
		gamesRepository.deleteById(id);
		return gameBody;
	} 

 }