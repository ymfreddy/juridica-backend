package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {

	private Long id;
	private Long idEmpresa;
	private String nombre;
	private String imagenNombre;
	private String imagenRuta;
}
