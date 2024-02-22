package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResumenDto {
	private Long id;
	private String codigoCliente;
	private String nombre;
	private String email;
	private String telefono;
}
