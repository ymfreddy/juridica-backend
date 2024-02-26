package com.mfb.adm.comm.requests;

import java.io.Serializable;
import java.util.List;

import com.mfb.adm.comm.dtos.ProductoMasivoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SolicitudCargaMasivaProductos implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nit;
	private Integer sucursal;
	private String descripcion;
	private Long nitProveedor;
	private List<ProductoMasivoDto> lista;
}
