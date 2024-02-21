package com.mfb.adm.comm.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreguntaDto {
	private Long id;
	private Date fecha;
	private String username;
	private String pregunta;
	private String respuesta;
	private String recursos;
}
