package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaLogin {
	private String token;
	private String usuario;
	private Long nit;
	private Long idEmpresa;

}
