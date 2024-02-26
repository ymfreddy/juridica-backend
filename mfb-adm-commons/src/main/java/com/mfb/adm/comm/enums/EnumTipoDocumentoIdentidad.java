package com.mfb.adm.comm.enums;

import lombok.Getter;

@Getter
public enum EnumTipoDocumentoIdentidad {

	CI("CI"), NIT("NIT");

	private final String codigo;

	EnumTipoDocumentoIdentidad(String codigo) {
		this.codigo = codigo;
	}
}
