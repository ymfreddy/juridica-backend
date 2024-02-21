package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLogin {

	private Long id;
	private Long idEmpresa;
	private Integer idTipoUsuario;
	private Long nit;
	private String username;
	private String password;
	private Boolean enabled;

}
