package com.mfb.adm.comm.enums;

public enum EnumEstadoTurno {

	ABIERTO(32), CERRADO(33);

	private Integer valor;

	private EnumEstadoTurno(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
