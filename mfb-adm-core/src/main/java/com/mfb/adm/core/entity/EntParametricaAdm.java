package com.mfb.adm.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBaseSinIdentity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "parametricas", schema = "adm")
@Where(clause = "active='true'")
public class EntParametricaAdm extends EntBaseSinIdentity {
	private String nombre;
	private String descripcion;
	private Integer valor;
	private String tipo;
}
