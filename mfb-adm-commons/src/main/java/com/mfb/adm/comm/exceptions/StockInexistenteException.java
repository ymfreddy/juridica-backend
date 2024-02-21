package com.mfb.adm.comm.exceptions;

public class StockInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public StockInexistenteException(String message) {
		super(message);
	}

	public StockInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}
}
