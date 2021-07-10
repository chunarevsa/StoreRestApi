package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Games;
import com.chunarevsa.Website.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

@RestController

public class GamesControllerGUI {

	@Autowired
	
	private GamesRepository gamesRepository;
	public GamesControllerGUI (GamesRepository gamesRepository) {
		this.gamesRepository = gamesRepository;
	} 

	// Получение списка всех игр с ограничением страницы (10)
	@RequestMapping (path = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Games> gamesFindAll (@PageableDefault(sort = { "id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		Page<Games> pageGames = gamesRepository.findAll(pageable);
		return pageGames;
	}

	// Получение игры по id
	@RequestMapping (path = "/games/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Games gamesMethod (@PathVariable(value = "id") long id) { 
		Games game = gamesRepository.findById(id).orElseThrow();
		return game;
	} 
	 
	// Добавление игры 
	@PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public long createdGame (@RequestBody Games newGames) {		
		gamesRepository.save(newGames);
		return newGames.getId();
		}	

	 // Изменение
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
	@DeleteMapping(value = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public long deleteGames (@PathVariable(value = "id") long id)	{
		gamesRepository.deleteById(id);
		return id;
	} 

 }