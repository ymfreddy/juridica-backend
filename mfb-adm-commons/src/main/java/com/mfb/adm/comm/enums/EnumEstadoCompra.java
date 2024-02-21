package com.mfb.adm.comm.enums;

public enum EnumEstadoCompra {

	SIN_RECIBIR(14), RECIBIDA(15);

	private Integer valor;

	private EnumEstadoCompra(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
