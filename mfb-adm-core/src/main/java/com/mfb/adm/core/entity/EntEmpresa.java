package com.mfb.adm.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBaseSinIdentity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empresas", schema = "adm")
@Where(clause = "active='true'")
public class EntEmpresa extends EntBaseSinIdentity {
	private String nombre;
	private String representanteLegal;
	private Long nit;
	private String sigla;
	private String email;
}
