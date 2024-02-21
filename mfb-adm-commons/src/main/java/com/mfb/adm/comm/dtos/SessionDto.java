package com.mfb.adm.comm.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDto {

	private UsuarioDto usuario;
	private List<OpcionDto> opciones;
}
