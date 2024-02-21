package com.mfb.adm.comm.enums;

public enum EnumCliente {

	POR_DEFECTO(1L);

	private Long valor;

	private EnumCliente(Long valor) {
		this.valor = valor;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}
}
