package com.desafioapp.dto;

import java.util.List;

import com.desafioapp.entity.Movie;
import com.desafioapp.models.ReturnStatus;

public class MoviesDTO {
	
	private List<Movie> movies;
	private ReturnStatus returnStatus;
	
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	public MoviesDTO(String year) {
		returnStatus = new ReturnStatus("00200", "/movies/"+year);
	}
	
	
}
