package com.mfb.adm.comm.enums;

public enum EnumEstadoVisita {

	ASIGNADA(60), 
	INICIADA(61), 
	FINALIZADA(62), 
	CANCELADA(63);

	private Integer valor;

	private EnumEstadoVisita(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
