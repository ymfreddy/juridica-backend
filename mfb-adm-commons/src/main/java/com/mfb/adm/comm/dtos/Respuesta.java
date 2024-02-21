package com.mfb.adm.comm.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta extends DatosRespuesta {
	@Builder
	public Respuesta(boolean success, String message) {
		super(success, message);
	}
}
