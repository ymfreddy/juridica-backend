package com.mfb.adm.comm.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespuestaListaPaginada implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer totalItems;
	private Integer paginaActual;
	private Integer paginasTotales;
	private Object items;	
}

