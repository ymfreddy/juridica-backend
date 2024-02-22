package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {
	private Long id;
	private Long idEmpresa;
	private String codigoCliente;
	private String codigoTipoDocumentoIdentidad;
	private String numeroDocumento;
	private String complemento;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
}
