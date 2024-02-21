package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDto {
	private Long id;
	private String nombre;
	private String representanteLegal;
	private Long nit;
	private String sigla;
	private String email;
}
