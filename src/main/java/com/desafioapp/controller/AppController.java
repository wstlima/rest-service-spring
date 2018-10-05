package com.desafioapp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.desafioapp.config.Greeting;
import com.desafioapp.config.Util;
import com.desafioapp.dto.AwardsIntervalDTO;
import com.desafioapp.dto.MovieWinDTO;
import com.desafioapp.dto.MovieDTO;
import com.desafioapp.dto.StudioOrderWinnsDTO;
import com.desafioapp.entity.AwardsInterval;
import com.desafioapp.entity.Movie;
import com.desafioapp.exception.BadRequestException;
import com.desafioapp.exception.ForbiddenException;
import com.desafioapp.exception.MethodNotAllowedException;
import com.desafioapp.exception.NotFoundException;
import com.desafioapp.models.AwardsIntervalWinnsCount;
import com.desafioapp.models.ReturnStatus;
import com.desafioapp.models.StudiosOrderWinns;
import com.desafioapp.repository.AwardsIntervalRepository;
import com.desafioapp.repository.MovieRepository;
import com.desafioapp.repository.StudioOrderWinnsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
@RestController
@RequestMapping(value = { "/" })
public class AppController {
	
	RequestMappingHandlerMapping requestMappingHandlerMapping;	
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private StudioOrderWinnsRepository studioRepository;	    
    
    @Autowired
    private AwardsIntervalRepository awardsIntervalRepository;	   
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greetings/{name}", method = RequestMethod.GET)
    public Greeting greetingMsg(@PathVariable String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping(value = "/greetings", method = RequestMethod.GET)
    public Greeting greeting() {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, "World"));
    }    

	@RequestMapping(value = "/movies/{year}", method = RequestMethod.GET)
	public MovieDTO listForYear(HttpServletRequest request, @PathVariable("year") String year)  
			throws BadRequestException {
		
		try {
			@SuppressWarnings("unused")
			int yearPar = Integer.parseInt(year);
		} catch (Exception e) {
			throw new BadRequestException("");
		}	
		
		if(year.length()!=4) {
			throw new BadRequestException("");
		}
		
		System.out.println("Ano: "+year);	
	
		List<Movie> data = movieRepository.findMoviesByYear(year);		
		MovieDTO moviesData = new MovieDTO(year);		
		moviesData.setMovies(data);		
		return moviesData;
	}
	
	@RequestMapping(value = "/movies/yearsmoreonewinners")
	public MovieWinDTO listWinnersMoreOneYear(HttpServletRequest request) 
			throws MethodNotAllowedException{
		
		String strMethod = "GET";		
		System.out.println("Method: "+request.getMethod());
		if(!request.getMethod().equals(strMethod)) {
			throw new MethodNotAllowedException("");
		}		
		
		Iterable<AwardsIntervalWinnsCount> ite = movieRepository.moviesWinnersMoreOneYear();
		List<AwardsIntervalWinnsCount> listagem = Util.toList(ite);	
		
		@SuppressWarnings({ "rawtypes" })
		Iterator itr = listagem.iterator();		
	
		List<AwardsIntervalWinnsCount> dataWinn = new ArrayList<AwardsIntervalWinnsCount>();	
		MovieWinDTO dataYears = new MovieWinDTO();	
		
		while(itr.hasNext()) {
		    Object[] arrObj = (Object[])itr.next();		    
		    dataWinn.add(new AwardsIntervalWinnsCount(String.valueOf(arrObj[0]), Integer.parseInt(String.valueOf(arrObj[1]))));		  
		}
		
		dataYears.setYears(dataWinn);    
		return dataYears;	
	}	
	
	@RequestMapping(value = "/movies/studiosorderwinns")
	public StudioOrderWinnsDTO listForStudios(HttpServletRequest request) 
			throws MethodNotAllowedException {
		
		String strMethod = "GET";		
		System.out.println("Method: "+request.getMethod());
		if(!request.getMethod().equals(strMethod)) {
			throw new MethodNotAllowedException("");
		}			
		
		Iterable<StudiosOrderWinns> ite = studioRepository.studiosOrderWinns();
		List<StudiosOrderWinns> listagem = Util.toList(ite);
		
		@SuppressWarnings("rawtypes")
		Iterator itr = listagem.iterator();		
		
		List<StudiosOrderWinns> orderWinn = new ArrayList<StudiosOrderWinns>();	
		StudioOrderWinnsDTO dataStudios = new StudioOrderWinnsDTO();	
		
		while(itr.hasNext()) {
		    Object[] arrObj = (Object[])itr.next();		    
		    orderWinn.add(new StudiosOrderWinns(String.valueOf(arrObj[0]), Integer.parseInt(String.valueOf(arrObj[1]))));		  
		}		
		
		dataStudios.setStudios(orderWinn);    
		return dataStudios;	
	}	

	@RequestMapping(value = "/movies/awardsinterval")
	public AwardsIntervalDTO listForAwardsInterval(HttpServletRequest request) 
			throws MethodNotAllowedException {
		
		String strMethod = "GET";		
		System.out.println("Method: "+request.getMethod());
		if(!request.getMethod().equals(strMethod)) {
			throw new MethodNotAllowedException("");
		}			

		
		Iterable<AwardsInterval> ite = awardsIntervalRepository.awardsInterval();
		List<AwardsInterval> listagem = Util.toList(ite);	
		
		@SuppressWarnings({ "rawtypes" })
		Iterator itr = listagem.iterator();		
		
		List<AwardsInterval> intervalMin = new ArrayList<AwardsInterval>();	
		List<AwardsInterval> intervalMax = new ArrayList<AwardsInterval>();	
		AwardsIntervalDTO awardsIntervalDTO = new AwardsIntervalDTO();	
		
		String producer;
		int interval;
		int previousWin;
		int followingWin;		
		int i = 0;
			
		while(itr.hasNext()) {
			i++;
		    Object[] arrObj = (Object[])
		    		
		    itr.next();	
			producer = String.valueOf(arrObj[0]);
			interval = Integer.parseInt(String.valueOf(arrObj[1]));
			previousWin = Integer.parseInt(String.valueOf(arrObj[2]));
			followingWin = Integer.parseInt(String.valueOf(arrObj[3]));	
		    
		    if(i==1) {	// MAX INTERVAL			
		    	intervalMax.add(new AwardsInterval(producer, interval, previousWin, followingWin)); 
		    }else{  	// MIN INTERVAL		
		    	intervalMin.add(new AwardsInterval(producer, interval, previousWin, followingWin)); 		    	
		    }
		}	
		
		awardsIntervalDTO.setMin(intervalMin);   
		awardsIntervalDTO.setMax(intervalMax); 
		return awardsIntervalDTO;	
	}

	@RequestMapping(value = "/movies/{id}", produces = "application/json")
	public ResponseEntity<ReturnStatus> deleteMovie(HttpServletRequest request, @PathVariable("id") String id, final HttpServletResponse response) 
			throws ForbiddenException, NotFoundException, MethodNotAllowedException, JsonProcessingException, BadRequestException {
		
		String strMethod = "DELETE";		
		System.out.println("Method: "+request.getMethod());
		if(!request.getMethod().equals(strMethod)) {
			throw new MethodNotAllowedException("");
		}
		
		Long idPar;
		
		try {
			idPar = Long.parseLong(id);
		} catch (Exception e) {
			throw new BadRequestException("");
		}			
		
		ReturnStatus returnStatus = new ReturnStatus("00201", request.getRequestURL().toString());		
		Movie movie = movieRepository.findOne(idPar);		
		
	    if (movie == null) {
	        throw new NotFoundException("");
	    }else{	    
	    	System.out.println("Vencedor: "+movie.getWinner());
		    if (movie.getWinner()) {
		        throw new ForbiddenException("");
		    }else {
		    	movieRepository.delete(movie);    	
		    }
	    }		
		return new ResponseEntity<>(returnStatus, HttpStatus.OK);
	}	

}
