package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueCode", columnNames = { "code"}) })
@Data
public class Currency extends Base {

	@Column(unique=true)
	private String code;	

}
