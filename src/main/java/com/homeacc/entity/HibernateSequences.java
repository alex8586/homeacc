package com.homeacc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HIBERNATE_SEQUENCES")
public class HibernateSequences implements java.io.Serializable {

	private long id;
	private String sequenceName;
	private long nextVal;

	public HibernateSequences() {
	}

	public HibernateSequences(long id, String sequenceName, long nextVal) {
		this.id = id;
		this.sequenceName = sequenceName;
		this.nextVal = nextVal;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "SEQUENCE_NAME", nullable = false, length = 40)
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	@Column(name = "NEXT_VAL", nullable = false, precision = 12, scale = 0)
	public long getNextVal() {
		return this.nextVal;
	}

	public void setNextVal(long nextVal) {
		this.nextVal = nextVal;
	}

}
