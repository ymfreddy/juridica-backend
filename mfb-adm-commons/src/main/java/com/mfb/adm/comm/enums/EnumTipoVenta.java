package com.mfb.adm.comm.enums;

public enum EnumTipoVenta {

	CONTADO(23), CREDITO(24), ONLINE(25);

	private Integer valor;

	private EnumTipoVenta(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
