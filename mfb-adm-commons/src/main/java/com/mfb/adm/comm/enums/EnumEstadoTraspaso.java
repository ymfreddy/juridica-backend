package com.mfb.adm.comm.enums;

public enum EnumEstadoTraspaso {

	SOLICITADO(26), TRASPASADO(27);

	private Integer valor;

	private EnumEstadoTraspaso(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
