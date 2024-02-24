package com.mfb.adm.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "correlativos", schema = "adm")
@Where(clause = "active='true'")
public class EntCorrelativo extends EntBase {
	private Long idEmpresa;
	private Short gestion;
	private String operacion;
	private Long secuencia;
}
