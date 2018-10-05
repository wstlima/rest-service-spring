package com.desafioapp.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafioapp.models.ReturnStatus;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ReturnStatus> handleAllExceptions(Exception ex, WebRequest request, HttpServletRequest request2) {
	ReturnStatus returnStatus = new ReturnStatus("00500", request2.getRequestURL().toString());
    return new ResponseEntity<>(returnStatus, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ForbiddenException.class)
  public final ResponseEntity<ReturnStatus> handleUserForbiddenException(ForbiddenException ex, WebRequest request, HttpServletRequest request2) {
	  ReturnStatus returnStatus = new ReturnStatus("00403", request2.getRequestURL().toString());
    return new ResponseEntity<>(returnStatus, HttpStatus.FORBIDDEN);
  }
  
  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ReturnStatus> handleUserNotFoundException(NotFoundException ex, WebRequest request, HttpServletRequest request2) {
	  ReturnStatus returnStatus = new ReturnStatus("00404", request2.getRequestURL().toString());
    return new ResponseEntity<>(returnStatus, HttpStatus.NOT_FOUND);
  }  
  
  @ExceptionHandler(MethodNotAllowedException.class)
  public final ResponseEntity<ReturnStatus> handleMethodNotAllowedException(MethodNotAllowedException ex, WebRequest request, HttpServletRequest request2) {
	  ReturnStatus returnStatus = new ReturnStatus("00405", request2.getRequestURL().toString());
    return new ResponseEntity<>(returnStatus, HttpStatus.METHOD_NOT_ALLOWED);
  }    
  
  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ReturnStatus> handleBadRequestException(BadRequestException ex, WebRequest request, HttpServletRequest request2) {
	  ReturnStatus returnStatus = new ReturnStatus("00400", request2.getRequestURL().toString());
    return new ResponseEntity<>(returnStatus, HttpStatus.BAD_REQUEST);
  }    

}