package com.mfb.adm.api.rest.exceptions;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.exceptions.SaldoInsuficienteException;
import com.mfb.adm.comm.exceptions.UnauthorizedException;
import com.mfb.adm.api.rest.utils.MentionedFileNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class RestDefaultExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
		return new ResponseEntity<>(RespuestaRest.builder().message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MentionedFileNotFoundException.class)
	public ResponseEntity<Object> exception(MentionedFileNotFoundException e) {
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? NestedExceptionUtils.getMostSpecificCause(e).getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<Object> exception(SaldoInsuficienteException e) {
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? NestedExceptionUtils.getMostSpecificCause(e).getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> exception(DataIntegrityViolationException e) {
		e.printStackTrace();	
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? NestedExceptionUtils.getMostSpecificCause(e).getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLDataException.class)
	public ResponseEntity<Object> exception(SQLDataException e) {
		e.printStackTrace();
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? NestedExceptionUtils.getMostSpecificCause(e).getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleException(Exception e) {
		log.error("Exception " +MsgApp.RESPONSE_SERVER_ERROR, e);
		log.error("Exception message" + e.getMessage());
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? e.getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException " +MsgApp.RESPONSE_SERVER_ERROR, e);
		e.printStackTrace();
		List<String> errores = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errores.add(error.getField() + ": " + error.getDefaultMessage());
		}
		String mensaje = MsgApp.RESPONSE_SERVER_ERROR_VALIDATION + ": " + String.join(", ", errores);
		return new ResponseEntity<>(RespuestaRest.builder().success(false).message(mensaje).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public final ResponseEntity<?> handleException(NullPointerException e) {
		log.error("NullPointerException " +MsgApp.RESPONSE_SERVER_ERROR, e);
		e.printStackTrace();
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message(MsgApp.RESPONSE_NULL_POINTER).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	public final ResponseEntity<?> handleException(IOException e) {
		log.error("IOException " +MsgApp.RESPONSE_SERVER_ERROR, e);
		e.printStackTrace();
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? e.getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException " +MsgApp.RESPONSE_SERVER_ERROR, e.getMessage());
		return new ResponseEntity<>(
				RespuestaRest.builder().success(false).message((e != null ? e.getMessage() : "")).build(),
				HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<?> handleException(ConstraintViolationException ex) {
		List<String> errores = new ArrayList<>();
		ex.getConstraintViolations().forEach((error) -> {
			String fieldName = error.getPropertyPath().toString();
			String errorMessage = error.getMessageTemplate();
			errores.add(fieldName + ": " + errorMessage);
		});

		String mensaje = MsgApp.RESPONSE_SERVER_ERROR_VALIDATION + ": " + String.join(", ", errores);
		return new ResponseEntity<>(RespuestaRest.builder().success(false).message(mensaje).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public final ResponseEntity<?> customHandleNotFound(Exception ex, WebRequest request) {
		return new ResponseEntity<>(RespuestaRest.builder().success(false).message(MsgApp.RESPONSE_JWT_ERROR).build(),
				HttpStatus.FORBIDDEN);

	}
}