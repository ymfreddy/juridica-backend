package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcionDto {
	private Integer id;
	private String titulo;
	private String ruta;
	private String icono;
	private String estilo;
	private String grupo;
	private Short orden;
	private String descripcion;
}
