package com.mfb.adm.comm.requests;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SolicitudBusquedaPreguntas implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String fechaInicio;
	private String fechaFin;

}
