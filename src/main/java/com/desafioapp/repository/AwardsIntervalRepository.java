package com.desafioapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafioapp.entity.AwardsInterval;
import com.desafioapp.entity.Movie;

public interface AwardsIntervalRepository extends JpaRepository<Movie, Long> {
	String strQry = "SELECT * FROM AWARDSINTERVAL_VIEW ";      
    @Query(value = strQry, nativeQuery = true)
    List<AwardsInterval> awardsInterval();	
}