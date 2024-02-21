package com.mfb.adm.comm.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class SolicitudListaPaginada {
	private Long idEmpresa;
	private Integer cantidadItems;
	private Integer pagina;
	private String campoOrden;
	private Integer tipoOrden;
	private String filtro;
	private String campoEspecifico;
	private String username;
}
