package com.mfb.adm.comm.dtos;

import java.util.List;

import lombok.Data;

@Data
public class DatosEmail {
	private String emailDestino;
	private String emailOrigen;
	private String emailCopia;
	private String asunto;
	//private String contenidoTipo;
	private List<DatosAdjunto> adjuntos;
	private String texto;
	//private String lugar;
}
