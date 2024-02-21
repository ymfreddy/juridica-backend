package com.mfb.adm.comm.enums;

public enum EnumTipoProducto {

	PRODUCTO_SERVICIO(13), CON_INVENTARIO(12), SERVICIO_HOTEL_TURISMO(11);

	private Integer valor;

	private EnumTipoProducto(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
