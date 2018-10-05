package app;

import static com.jayway.restassured.RestAssured.when;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class AppControllerIT {	
	
	// Cenário esperado
	// Obs.: Precisa parar a aplicação e iniciar novamente para que se tenha como excluir o filme, 
	//       pois caso contrário dará falha na hora de testar
	
	@Test
    public void canFetchMoviesForYear() {
        when().
                get("/movies/2017").
        then().
                statusCode(HttpStatus.SC_OK).
                body("returnStatus.codreturn", Matchers.containsString("00200"));               
    }
	
	@Test
    public void canFetchWinnersMoreOneYear() {
        when().
                get("/movies/yearsmoreonewinners").
        then().
                statusCode(HttpStatus.SC_OK).
                body("returnStatus.codreturn", Matchers.containsString("00200"));               
    }	
	
	@Test
    public void canFetchStudiosOrderWinns() {
        when().
                get("/movies/studiosorderwinns").
        then().
                statusCode(HttpStatus.SC_OK).
                body("returnStatus.codreturn", Matchers.containsString("00200"));               
    }
	
	@Test
    public void canFetchAwardsInterval() {
        when().
                get("/movies/awardsinterval").
        then().
                statusCode(HttpStatus.SC_OK).
                body("returnStatus.codreturn", Matchers.containsString("00200"));               
    }	
	
	@Test
    public void canDeleteMovie() {
        when().
                delete("/movies/31").
        then().
                statusCode(HttpStatus.SC_OK).
                body("codreturn", Matchers.containsString("00201"));               
    }	
	
	//Cenário provocado
	
	@Test
    public void canDeleteMovieWinner() { // Filme vencedor
        when().
                delete("/movies/27").
        then().
                statusCode(HttpStatus.SC_FORBIDDEN).
                body("codreturn", Matchers.containsString("00403"));               
    }		
	

	@Test
    public void canDeleteMovieVerbPost() {
        when().
                post("/movies/31").
        then().
                statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED).
                body("codreturn", Matchers.containsString("00405"));               
    }		
	
	
	@Test
    public void canNotExistPath1() {
        when().
                get("/").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);               
    }	
	
	@Test
    public void canNotExistPath2() {
        when().
                get("/movies").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);               
    }	
	
	@Test
    public void canNotExistPath3() {
        when().
                get("/movies/xpto").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);               
    }	
	
	@Test
    public void canNotExistPath4() {
        when().
                get("/movies/xpto/xpto").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);               
    }		
	
	

}
