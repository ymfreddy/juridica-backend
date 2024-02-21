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
@Table(name = "limites", schema = "adm")
@Where(clause = "active='true'")
public class EntLimite extends EntBase {
	private String username;
	private String dia;
	private Integer cantidad;
}
