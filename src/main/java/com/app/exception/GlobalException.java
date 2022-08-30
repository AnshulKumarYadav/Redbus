package com.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> adminExceptionHandler(AdminException ae, WebRequest wr)
	{
		
		MyErrorDetails me = new MyErrorDetails(LocalDateTime.now(), ae.getMessage(), wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(me,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> adminExceptionHandler(UserException ae, WebRequest wr)
	{
		
		MyErrorDetails me = new MyErrorDetails(LocalDateTime.now(), ae.getMessage(), wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(me,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> adminExceptionHandler(Exception ae, WebRequest wr)
	{
		
		MyErrorDetails me = new MyErrorDetails(LocalDateTime.now(), ae.getMessage(), wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(me,HttpStatus.BAD_REQUEST);
		
	}

}
