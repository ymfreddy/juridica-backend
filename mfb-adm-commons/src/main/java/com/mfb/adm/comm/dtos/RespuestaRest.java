package com.mfb.adm.comm.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespuestaRest extends DatosRespuesta {
	private static final long serialVersionUID = 1L;
	private Object content;

	@Builder
	public RespuestaRest(boolean success, String message, Object content) {
		super(success, message);
		this.content = content;
	}
}
