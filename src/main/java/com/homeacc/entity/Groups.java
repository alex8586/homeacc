package com.homeacc.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
* Groups generated by hbm2java
*/
@Entity
@Table(name = "GROUPS")
public class Groups implements java.io.Serializable {

	private long id;
	private String name;
	private String password;
	private Set<Users> userses = new HashSet<Users>(0);
	private Set<BudgetRecord> budgetRecords = new HashSet<BudgetRecord>(0);
	private Set<Category> categories = new HashSet<Category>(0);

	public Groups() {
	}

	public Groups(long id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Groups(long id, String name, String password, Set<Users> userses, Set<BudgetRecord> budgetRecords,
			Set<Category> categories) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.userses = userses;
		this.budgetRecords = budgetRecords;
		this.categories = categories;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator",
		    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
		    @Parameter(name = "sequence_name", value = "seq_group"),
		    @Parameter(name = "initial_value", value = "50"),
		    @Parameter(name = "increment_size", value = "1"),
		    @Parameter(name = "value_column", value = "seq_count") })
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<BudgetRecord> getBudgetRecords() {
		return this.budgetRecords;
	}

	public void setBudgetRecords(Set<BudgetRecord> budgetRecords) {
		this.budgetRecords = budgetRecords;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}
