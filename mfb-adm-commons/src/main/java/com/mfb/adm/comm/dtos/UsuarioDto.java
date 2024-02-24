package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
	private Long id;
	private Long idEmpresa;
	private String codigoTipoUsuario;
	private String tipoUsuario;
	private String nombre;
	private String paterno;
	private String materno;
	private String nombreCompleto;
	private String ci;
	private Integer celular;
	private String username;
	private Boolean cambiarClave;
	private Boolean enabled;
	private String empresaNombre;
	private Long empresaNit;
	private Long idSucursal;
	private String email;
	private String opciones;
	private String asociaciones;
	private String categorias;
	private Boolean impresionDirecta;
	private Boolean descripcionAdicionalProducto;
}
