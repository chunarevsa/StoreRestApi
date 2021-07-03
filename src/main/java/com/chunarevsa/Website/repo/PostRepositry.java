package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepositry extends CrudRepository<Post, Long>{
	/* CrudRepository встроенный интерфейс для таблиц. 
	Можно добавлять записи, удалить, обновить, вытянуть
	<Post, Long> - указываем с какой моделью мы работаем 
	и тип данных ид*/

	/*больше ничего не прописываем, потому что всё 
	будем делать через CrudRepository */
}
