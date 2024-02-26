package com.mfb.adm.comm.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoResumenDto {

	private Long id;
	private Integer idTipoProducto;
	private String codigoProducto;
	private String nombre;
	private BigDecimal precio;
	private String imagenRuta;
	private String imagenNombre;
	//private List<SaldoProductoDto> saldos;
	//private DescuentoProductoDto descuento;
	private Integer codigoTipoHabitacion;
	private String tipoHabitacion;
	private BigDecimal precioIce;
}
