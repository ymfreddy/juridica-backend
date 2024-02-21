package com.mfb.adm.comm.enums;

import java.util.Arrays;
import java.util.List;

public enum EnumMotivoCancelacionVisita {

	UERGENCIA_INTERESADO(64), 
	CAMBIO_DE_FECHA(65), 
	OTRO(66);


	private Integer valor;

	private EnumMotivoCancelacionVisita(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	static List<Integer> LISTA = Arrays.asList(64, 65, 66);
	
	public static Boolean esMotivoAnulacion(Integer codigo) {
		return LISTA.contains(codigo);
	}
}
