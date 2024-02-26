package com.mfb.adm.core.entity;

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
@Table(name = "categorias", schema = "spv")
@Where(clause = "active='true'")
public class EntCategoria extends EntBase {
	private String nombre;
	private Long idEmpresa;
	private String imagenNombre;
}
