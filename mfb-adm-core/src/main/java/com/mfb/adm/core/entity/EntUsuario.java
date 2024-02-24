package com.mfb.adm.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usuarios", schema = "adm")
@Where(clause = "active='true'")
public class EntUsuario extends EntBase {
	@Column(name = "id_empresa")
	private Long idEmpresa;
	@Column(name = "codigo_tipo_usuario")
	private String codigoTipoUsuario;
	private String nombre;
	private String paterno;
	private String materno;
	private String ci;
	private Integer celular;
	private String username;
	private String password;
	private Boolean cambiarClave;
	private Boolean enabled;
	//private Long idSucursal;
	private String email;
	private String opciones;
}
