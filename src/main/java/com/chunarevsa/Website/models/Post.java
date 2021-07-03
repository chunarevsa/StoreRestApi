package com.chunarevsa.Website.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity // обозначаем что это модель
// она отвечает за определенную табличку хранит статьи

public class Post {
	
	/* добавляем характеристики/переменные/поля (одно и тоже) 
	Например ID; название; текст; дата создания...) */

	@Id // добавляем аннотацию для ид
	@GeneratedValue(strategy = GenerationType.AUTO) 
	// генерирует для новой записи уникальный ид
	private Long id; // Long потому что статей может огромное кол.
	private String title, anons, full_text;
	private int views; // добавляем кол. просмотров

	 /* Далее создаём функции, которые будут получать 
	значения из каждого поля и далее устанавливать их */
	public Long getId() {
		return this.id; // Почему-то в IntellieJ  без this в git..
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnons() {
		return this.anons;
	}

	public void setAnons(String anons) {
		this.anons = anons;
	}

	public String getFull_text() {
		return this.full_text;
	}

	public void setFull_text(String full_text) {
		this.full_text = full_text;
	}

	public int getViews() {
		return this.views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Post() { // пустой конструктор который не принимает никаких параметров
	}

	// Создаём конструтор принимающий 3 параметра
	public Post(String title, String anons, String full_text) { 
		this.title = title;
		this.anons = anons;
		this.full_text = full_text;
	}
	
}
