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
public class SolicitudBusquedaProductos implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEmpresa;
	private Long idSucursal;
	private Long idProducto;
	private String codigosTipoProducto;
	private Long idCategoria;
	private String idsCategorias;
	private String codigoProducto;
	private Boolean resumen;
	
	private String termino;
	private Integer cantidadRegistro;

}
