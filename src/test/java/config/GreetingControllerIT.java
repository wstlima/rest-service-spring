package config;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.desafioapp.Application;
import com.desafioapp.controller.AppController;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GreetingControllerIT {

	@Autowired
	AppController greetingController;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		ReflectionTestUtils.setField(greetingController, "counter", new AtomicLong());
	}

    @Test
    public void canFetchFirstHelloWorldGreeting() {
        when().
                get("/greetings").
        then().
                statusCode(HttpStatus.SC_OK).
                body("id", Matchers.is(1)).
                body("content", Matchers.is("Hello, World!"));
    }

    @Test
    public void canFetchFirstNameEchoGreeting() {
    	given().
        	pathParam("name", "Nico").
    	when().
        	get("/greetings/{name}").
        then().
        	statusCode(HttpStatus.SC_OK).
        	body("id", Matchers.is(1)).
        	body("content", Matchers.is("Hello, Nico!"));
    }

    @Test
    public void canFetchFirstAndSecondNameEchoGreeting() {
    	given().
        	pathParam("name", "Well").
    	when().
        	get("/greetings/{name}").
        then().
        	statusCode(HttpStatus.SC_OK).
        	body("id", Matchers.is(1)).
        	body("content", Matchers.is("Hello, Well!"));

    	given().
    		pathParam("name", "Well").
    	when().
    		get("/greetings/{name}").
    	then().
    		statusCode(HttpStatus.SC_OK).
    		body("id", Matchers.is(2)).
    		body("content", Matchers.is("Hello, Well!"));
    }

    @Test
    public void crossOriginResourceSharingPermitted() {
    	when().
    		options("/greetings").
    	then().
    		statusCode(HttpStatus.SC_OK).
    		header("Access-Control-Allow-Origin", "*").
    		header("Access-Control-Allow-Methods", "GET, OPTIONS").
    		header("Access-Control-Max-Age", "3600").
    		header("Access-Control-Allow-Headers", "x-requested-with");
    }
    
  

}
