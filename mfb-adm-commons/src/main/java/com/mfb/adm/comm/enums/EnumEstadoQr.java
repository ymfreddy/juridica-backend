package com.mfb.adm.comm.enums;

public enum EnumEstadoQr {

	//1=No Usado; 2= Usado 3=Expirado; 4=Con error
	NO_USADO(1), USADO(2), EXPIRADO(3), CON_ERROR(4);

	private Integer valor;

	private EnumEstadoQr(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
