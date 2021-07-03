package com.chunarevsa.Website.controllers;


// import java.util.ArrayList;
// import java.util.Optional;

// import com.chunarevsa.Website.models.Post;
// import com.chunarevsa.Website.repo.PostRepositry;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
 

@Controller // добавляем, чтобы указать, что это контроллер
public class GamesController {

 	@GetMapping ("/games")
 	public String gamesgMain (Model model) {
		 return "games-main";

 	}

	

}