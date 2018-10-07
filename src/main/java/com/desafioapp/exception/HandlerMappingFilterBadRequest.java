package com.desafioapp.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.desafioapp.models.ReturnStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
@RestController
public class HandlerMappingFilterBadRequest extends OncePerRequestFilter {
	RequestMappingHandlerMapping requestMappingHandlerMapping;
	private ReturnStatus returnStatus;

	public HandlerMappingFilterBadRequest(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	public ReturnStatus badRequest(HttpServletRequest request )  {
		this.returnStatus = new ReturnStatus("00400", request.getRequestURL().toString());
		return this.returnStatus;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletResponse hsr = (HttpServletResponse) response;
		ObjectMapper mapper = new ObjectMapper();

		this.badRequest(request);
		String jsonInString = mapper.writeValueAsString(this.returnStatus);
		String responseToClient = jsonInString;

		try {
			System.out.println(jsonInString);
			hsr.setContentType("application/json;charset=UTF-8");
			hsr.setStatus(400);
			hsr.getWriter().write(responseToClient);
			hsr.getWriter().flush();
			hsr.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		Collection<String> excludeUrlPatterns = new ArrayList<>();
		excludeUrlPatterns.add("/health");	
		excludeUrlPatterns.add("/greetings");	
		excludeUrlPatterns.add("/greetings/**");		
		excludeUrlPatterns.add("/movies/*");
		excludeUrlPatterns.add("/h2/**");
		excludeUrlPatterns.add("/movies/yearsmoreonewinners");
		excludeUrlPatterns.add("/movies/studiosorderwinns");
		excludeUrlPatterns.add("/movies/awardsinterval");
		AntPathMatcher pathMatcher = new AntPathMatcher();

		return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
	}

	@Override
	public void destroy() {

	}

	public HandlerMappingFilterBadRequest() {
		super();
	}
}
