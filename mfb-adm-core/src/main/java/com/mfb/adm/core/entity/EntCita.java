package com.mfb.adm.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBaseUUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "citas", schema = "adm")
@Where(clause = "active='true'")
public class EntCita extends EntBaseUUID {
	private static final long serialVersionUID = 1L;
	@Column(nullable = false, updatable = false)
	private Long idEmpresa;
	@Column(nullable = false, updatable = false)
	private Long correlativo;
	private Long idCliente;
	private Date fecha;
	private String inicio;
	private String fin;
	private String nota;
	private String codigoEstado;
	private String codigoTipo;
}
