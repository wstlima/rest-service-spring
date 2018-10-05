package com.desafioapp.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@javax.persistence.Entity(name = "MOVIES")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "year", length = 4, nullable = false)
	private String year;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@OneToMany
	@JoinTable(name = "STUDIOS", joinColumns = @JoinColumn(name = "FK_ID_MOVIE"), inverseJoinColumns = @JoinColumn(name = "ID"))
	private List<Studio> studios;

	@OneToMany
	@JoinTable(name = "PRODUCERS", joinColumns = @JoinColumn(name = "FK_ID_MOVIE"), inverseJoinColumns = @JoinColumn(name = "ID"))
	private List<Producer> producers;
	
	@Column(name = "winner")
	private Boolean winner;	

	public Movie() {
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}

	public List<Studio> getStudios() {
		return studios;
	}

	public void setStudios(List<Studio> studios) {
		this.studios = studios;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

}
