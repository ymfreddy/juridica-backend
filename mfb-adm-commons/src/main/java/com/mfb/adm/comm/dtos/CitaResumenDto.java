package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaResumenDto {
	private String id;
	private Long correlativo;
	private String inicio;
	private String fin;
	private String color;
	private String descripcion;
}
