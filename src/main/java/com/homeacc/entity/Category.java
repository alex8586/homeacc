package com.homeacc.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
* Category generated by hbm2java
*/
@Entity
@Table(name = "CATEGORY")
public class Category implements java.io.Serializable {

	private long id;
	private Groups groups;
	private String name;
	private Set<BudgetRecord> budgetRecords = new HashSet<BudgetRecord>(0);

	public Category() {
	}

	public Category(long id, Groups groups, String name) {
		this.id = id;
		this.groups = groups;
		this.name = name;
	}

	public Category(long id, Groups groups, String name, Set<BudgetRecord> budgetRecords) {
		this.id = id;
		this.groups = groups;
		this.name = name;
		this.budgetRecords = budgetRecords;
	}

	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "generator", sequenceName = "category_id", initialValue = 50, allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = false)
	public Groups getGroups() {
		return this.groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@Column(name = "NAME", nullable = false, length = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<BudgetRecord> getBudgetRecords() {
		return this.budgetRecords;
	}

	public void setBudgetRecords(Set<BudgetRecord> budgetRecords) {
		this.budgetRecords = budgetRecords;
	}

}

