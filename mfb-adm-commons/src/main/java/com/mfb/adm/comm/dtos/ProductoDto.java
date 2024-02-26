package com.mfb.adm.comm.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductoDto {

	private Long id;
	private String codigoTipoProducto;
	@NotNull
	private Long idCategoria;
	@NotNull
	private Long idEmpresa;
	@NotNull
	private String codigoProducto;
	private String codigoActividadSin;
	private Integer codigoProductoSin;
	private Integer codigoTipoUnidadSin;
	@NotNull
	private String nombre;
	private String descripcion;
	private BigDecimal costo;
	@NotNull
	private BigDecimal precio;
	private String imagenNombre;
	private String imagenRuta;
	private String tipoProducto;
	private String categoria;

	//private DescuentoProductoDto descuento;
}
