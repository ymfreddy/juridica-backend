package com.mfb.adm.core.entity;

import java.math.BigDecimal;

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
@Table(name = "productos", schema = "spv")
@Where(clause = "active='true'")
public class EntProducto extends EntBase {

	private String codigoProducto;
	private String nombre;
	private String descripcion;
	private BigDecimal costo;
	private BigDecimal precio;
	private String codigoTipoProducto;
	private Long idCategoria;

	private Long idEmpresa;
	private String imagenNombre;

	private String codigoActividadSin;
	private Integer codigoProductoSin;
	private Integer codigoTipoUnidadSin;
}
