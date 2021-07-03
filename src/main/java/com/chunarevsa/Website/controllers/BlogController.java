package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Post;
import com.chunarevsa.Website.repo.PostRepositry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // добавляем, чтобы указать, что это контроллер
public class BlogController {

	/* подключаем передачу всех записей в blog-main 
	из таблички Post*/

	//Создаём переменну которая будет ссылаться на PostRepository
	@Autowired
	private PostRepositry postRepositry1; // переменная
	// обязательно должен быть импорт PostRepositry сверху 

	@GetMapping ("/blog")
	public String blogMain (Model model) {
		Iterable<Post> posts = postRepositry1.findAll();   /* Массив данных из таблицы
		 По сути мы создаём объкт posts и обращаемся к репозиторию
		 postRepositry1. - вызываем функции для работы с таблицей */
		model.addAttribute("posts", posts);
		/* обращаемся к модели, передеаём в"posts" значение posts */

		return "blog-main";
	}

	
	@GetMapping ("/blog/add")
	public String blogAdd (Model model) {
		return "blog-add";
	}


	 /* Сделаем отслеживание перехода не через GetMapping, 
	а через post (его мы указывали в blog-add) */
	@PostMapping("/blog/add") // срабатывает только когда нажимается добавить статью 
	public String blogPostAdd(@RequestParam String title,  @RequestParam String anons, @RequestParam String full_text, Model model) {
	/*  @RequestParam String -добавляем получение новых параметров типом строка
 		title,anons и full_text - название берем из blog-add*/
		 Post post = new Post(title, anons, full_text); // Выделяем память под объект пост на основе модели Post
		 postRepositry1.save(post);	// Сохранение данных (Поста ) в табличку
		return "redirect:/blog"; // переадресация пользователя на главную страничку
	}


	// Создаём динамическую страничку (при переходе на "Детальнее")
	@GetMapping ("/blog/{id}") // отслеживание по динамическому параметру (id меняется)
	public String blogDetails (@PathVariable(value = "id") long id, Model model) {
		/*  @PathVariable (value = "id") - берём динамическое значение(параметр id) из URL, 
		long id - создаём параметр  */

		// Добавляем проверку, чтобы не созданные страницы выходили на шаблон
		if (!postRepositry1.existsById(id)) { // existsById(arg0) - возвращает false если запись была не найдена !
				return "redirect:/blog"; // Перенаправляем если не найдена
		}
		// existsById(arg0) - возвращает true если запись была найдена


		// Ищем из базы данных по id и отображаем в шаблоне
		Optional<Post> post = postRepositry1.findById(id); // ищем конкретный пост и помещаем в объект
		ArrayList<Post> res = new ArrayList<>();  // Переводим в ArrayList 
		post.ifPresent(res::add);; // обращаемся к объекту.переводим

		model.addAttribute("post", res);
		return "blog-details";
	}

	// Редактирование поста
	@GetMapping ("/blog/{id}/edit") 
	public String blogEdit (@PathVariable(value = "id") long id, Model model) {
		
		if (!postRepositry1.existsById(id)) { 
				return "redirect:/blog"; 
		}
		Optional<Post> post = postRepositry1.findById(id); 
		ArrayList<Post> res = new ArrayList<>();  
		post.ifPresent(res::add);; 
		model.addAttribute("post", res);
		return "blog-edit";
	}
	@PostMapping("/blog/{id}/edit") 
	public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title,  @RequestParam String anons, @RequestParam String full_text, Model model) {
		 Post post = postRepositry1.findById(id).orElseThrow();	// Создайём объект, обращаемсяк реп.находим по ид.выбрасывает исключение если не найдена
		 post.setTitle(title); // Переназначаем черз setter
		 post.setAnons(anons);
		 post.setFull_text(full_text);
		 postRepositry1.save(post); // Так как пост уже был создан, мы его перезаписываем
		return "redirect:/blog";
	}

	// Удаление поста
	@PostMapping("/blog/{id}/remove") 
	public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
		 Post post = postRepositry1.findById(id).orElseThrow();	// Создайём объект, обращаемсяк реп.находим по ид.выбрасывает исключение если не найдена
		 postRepositry1.delete(post); // Удаляем
		return "redirect:/blog";
	}

}
