package com.mfb.adm.comm.requests;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class SolicitudListaPaginadaProductos implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEmpresa;
	private Integer cantidadItems;
	private Integer pagina;
	private String campoOrden;
	private Integer tipoOrden;
	private String filtro;
	private String campoEspecifico;
	
	private String codigosTipoProducto;
	private String idsCategorias;
	private Long idSucursal;
	private Long idProducto;
}
