package com.mfb.adm.comm.enums;

public enum EnumTipoHistorial {

	ENTRADA_COMPRA(34), ENTRADA_DEVOLUCION(35), ENTRADA_TRASPASO(36), ENTRADA_AJUSTE(37), 
	SALIDA_VENTA(38), SALIDA_TRASPASO(39), SALIDA_AJUSTE(40);

	private Integer valor;

	private EnumTipoHistorial(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
