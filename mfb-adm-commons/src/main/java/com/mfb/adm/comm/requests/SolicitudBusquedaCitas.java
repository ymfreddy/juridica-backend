package com.mfb.adm.comm.requests;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SolicitudBusquedaCitas implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEmpresa;
	private Long correlativo;
	private String codigoCliente;
	private String tiposCita;
	private String estadosCita;
	private Integer mes;
	private Integer gestion;
	private Boolean resumen;
	private String termino;
	private Integer cantidadRegistros;
}
