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
@Table(name = "recursos", schema = "adm")
@Where(clause = "active='true'")
public class EntRecurso extends EntBase {
	private String nombre;
	private String descripcion;
	private String sourceId;
}
