package com.desafioapp;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.desafioapp.data.CSVToDataBaseH2;
import com.desafioapp.exception.HandlerMappingFilterBadRequest;

@SpringBootApplication
public class Application {

    @Bean
    @Autowired
    public FilterRegistrationBean listHandlers(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        FilterRegistrationBean register = new FilterRegistrationBean();
        register.setFilter(new HandlerMappingFilterBadRequest(requestMappingHandlerMapping));
        register.setName("handlerListFilter");
        register.setUrlPatterns(Arrays.asList(new String[]{"/*"}));
        register.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return register;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
		// Loading CSV file to H2 Memory DataBase
		CSVToDataBaseH2 loadFile = new CSVToDataBaseH2();
		System.out.println("Status CSV TO H2: " + loadFile.loadCSVToH2());	
     }

}


