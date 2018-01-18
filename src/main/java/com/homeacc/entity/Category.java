package com.homeacc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY")
public class Category implements java.io.Serializable {

	private long id;
	private String name;

	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "generator", sequenceName = "category_id", initialValue = 50)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Column(name="NAME", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
