package com.mfb.adm.comm.enums;

public enum EnumEstadoVenta {

	PEDIDO(16), 
	REVERTIDA(20), 
	COBRADA(17), 
	CREDITO_POR_PAGAR(18),
	CREDITO_PAGADO(19);

	private Integer valor;

	private EnumEstadoVenta(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
