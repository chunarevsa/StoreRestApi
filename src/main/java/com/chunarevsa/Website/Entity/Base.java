package com.chunarevsa.Website.Entity;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class Base {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	private Date created;
	@LastModifiedDate
	private Date updated;
	@Enumerated(EnumType.STRING)
	private Status status;

	public Base() {}

	public Base( Date created, Date updated, Status status) {
		this.created = created;
		this.updated = updated;
		this.status = status;
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public Date getCreated() {return this.created;}
	public void setCreated(Date created) {this.created = created;}

	public Date getUpdated() {return this.updated;}
	public void setUpdated(Date updated) {this.updated = updated;}

	public Status getStatus() {return this.status;}
	public void setStatus(Status status) {this.status = status;}



}
