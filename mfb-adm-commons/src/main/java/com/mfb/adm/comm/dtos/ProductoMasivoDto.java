package com.mfb.adm.comm.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductoMasivoDto {
	@NotNull 
	private String codigoProducto;
	@NotNull
	private String nombre;
	private String descripcion;
	@NotNull
	private String categoria;
	@NotNull
	private Integer idTipoProducto;
	@NotNull
	private Integer codigoTipoUnidad;
	private BigDecimal cantidadMinimaAlerta;
	private BigDecimal costo;
	@NotNull
	private BigDecimal precio;
	private BigDecimal precioCompra;
	private String codigoActividadSin;
	private Integer codigoProductoSin;
	private String imagenNombre;
	private BigDecimal cantidad;
	private BigDecimal precioIce;
}
