package com.mfb.adm.comm.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParametricaDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String nombre;
	private String tipo;
}
