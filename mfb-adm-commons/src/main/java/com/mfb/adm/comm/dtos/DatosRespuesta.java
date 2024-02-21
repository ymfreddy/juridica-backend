package com.mfb.adm.comm.dtos;

import java.io.Serializable;

public class DatosRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;

	public DatosRespuesta() {
		super();
	}

	public DatosRespuesta(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
