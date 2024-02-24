package com.mfb.adm.comm.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaDto {
	private String id;
	private Long idEmpresa;
	@NotNull
	private Long idCliente;
	private String codigoCliente;
	private String nombreCliente;
	private String telefonoCliente;
	
	private Long correlativo;
	
	private Date fecha;
	private String inicio;
	private String fin;
	private String color;
	private String codigoEstado;
	private String codigoTipo;
	private String estado;
	private String tipo;
	private String nota;
	private String descripcion;
	private Integer gestion;
	private Integer mes;	
}
