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
@Table(name = "opciones", schema = "adm")
@Where(clause = "active='true'")
public class EntOpcion extends EntBase {
	private String titulo;
	private String ruta;
	private String icono;
	private String estilo;
}
