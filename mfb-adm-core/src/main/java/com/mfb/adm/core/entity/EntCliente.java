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
@Table(name = "clientes", schema = "adm")
@Where(clause = "active='true'")
public class EntCliente extends EntBase {
	@Column(nullable = false, updatable = false)
	private Long idEmpresa;
	private String codigoCliente;
	private String codigoTipoDocumentoIdentidad;
	private String numeroDocumento;
	private String complemento;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;	
}
