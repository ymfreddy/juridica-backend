package com.mfb.adm.core.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.mfb.base.entity.EntBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "preguntas", schema = "adm")
@Where(clause = "active='true'")
public class EntPregunta extends EntBase {
	private Date fecha;
	private String username;
	private String pregunta;
	private String respuesta;
	private String recursos;
}
