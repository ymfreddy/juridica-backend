package com.mfb.adm.comm.enums;

public enum EnumTipoDescuento {

	DESCUENTO_PORCENTAJE(43), DESCUENTO_MONTO(44), DESCUENTO_TOTAL(42);

	private Integer valor;

	private EnumTipoDescuento(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
