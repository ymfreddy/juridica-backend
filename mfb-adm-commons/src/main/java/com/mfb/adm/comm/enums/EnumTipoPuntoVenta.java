package com.mfb.adm.comm.enums;

import lombok.Getter;

@Getter
public enum EnumTipoPuntoVenta {

	COMISIONISTA(1), VENTANILLA_COBRANZA(2), MOVIL(3), YPFB(4), CAJERO(5);

	private final Integer codigo;

	EnumTipoPuntoVenta(Integer codigo) {
		this.codigo = codigo;

	}

}
