package com.mfb.adm.comm.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatosReporte {
	private String nombre;
	private Object archivo;
}
