package com.desafioapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.desafioapp.entity.Movie;
import com.desafioapp.models.AwardsIntervalWinnsCount;

public interface MovieRepository extends CrudRepository<Movie, Long> {
	@Query("select u from MOVIES u where u.year = ?1")
	List<Movie> findMoviesByYear(String year);

	List<Movie> findMoviesByWinner(Boolean winner);
	
	String strQry = "SELECT  year,  SUM(CASE WHEN winner=true THEN 1 ELSE 0 END) AS Wins FROM movies GROUP BY year HAVING WINS>1";	
    @Query(value = strQry, nativeQuery = true)
    List<AwardsIntervalWinnsCount> moviesWinnersMoreOneYear();    
}
