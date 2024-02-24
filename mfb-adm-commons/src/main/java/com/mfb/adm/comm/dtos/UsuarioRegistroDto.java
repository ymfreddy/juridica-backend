package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroDto {
	private Long id;
	private Long idEmpresa;
	private String codigoTipoUsuario;
	private String nombre;
	private String paterno;
	private String materno;
	private String ci;
	private Integer celular;
	private String username;
	private String password;
	private Boolean cambiarClave;
	private Boolean enabled;
	//private Long idSucursal;
	private String email;
	private String opciones;
}
