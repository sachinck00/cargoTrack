package org.jspiders.CourierAndLogisticsTrackingSystem.exceptions;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordFoundException(NoRecordFoundException e){
		ResponseStructure<String> res = new ResponseStructure<>();
		res.setMessage(e.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(res , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException e){
		ResponseStructure<String> res = new ResponseStructure<>();
		res.setMessage(e.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(res , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidContactNumberException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidContactNumberException(InvalidContactNumberException e){
		ResponseStructure<String> res = new ResponseStructure<>();
		res.setMessage(e.getMessage());
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(res , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidInputException(InvalidInputException e){
		ResponseStructure<String> res = new ResponseStructure<>();
		res.setMessage(e.getMessage());
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(res , HttpStatus.BAD_REQUEST);
	}
	
}
