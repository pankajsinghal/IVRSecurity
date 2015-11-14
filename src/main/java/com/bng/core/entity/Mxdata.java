package com.bng.core.entity;

// Generated Oct 24, 2013 12:10:31 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Mxdata generated by hbm2java
 */
@Entity
@Table(name = "mxdata")
public class Mxdata implements java.io.Serializable {

	private Integer id;
	private byte[] data;
	private Set<MxgraphVersion> mxgraphVersions = new HashSet<MxgraphVersion>(0);
	private Set<Mxgraph> mxgraphs = new HashSet<Mxgraph>(0);

	public Mxdata() {
	}

	public Mxdata(byte[] data, Set<MxgraphVersion> mxgraphVersions,
			Set<Mxgraph> mxgraphs) {
		this.data = data;
		this.mxgraphVersions = mxgraphVersions;
		this.mxgraphs = mxgraphs;
	}
	
	public Mxdata(byte[] data) {
		super();
		this.data = data;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "data")
	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mxdata")
	public Set<MxgraphVersion> getMxgraphVersions() {
		return this.mxgraphVersions;
	}

	public void setMxgraphVersions(Set<MxgraphVersion> mxgraphVersions) {
		this.mxgraphVersions = mxgraphVersions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mxdata")
	public Set<Mxgraph> getMxgraphs() {
		return this.mxgraphs;
	}

	public void setMxgraphs(Set<Mxgraph> mxgraphs) {
		this.mxgraphs = mxgraphs;
	}

	@Override
	public String toString() {
		return "Mxdata [id=" + id + ", data=" + data.toString()
				+ ", mxgraphVersions=" + mxgraphVersions + ", mxgraphs="
				+ mxgraphs + "]";
	}

}