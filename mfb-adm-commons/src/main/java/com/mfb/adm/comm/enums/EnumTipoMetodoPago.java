package com.mfb.adm.comm.enums;

import lombok.Getter;

@Getter
public enum EnumTipoMetodoPago {

	EFECTIVO(1), TARJETA(2)	, PAGO_POSTERIOR(6)
	, DEPOSITO_EN_CUENTA(8)
	, ONLINE(33)
	;

	private final Integer codigo;

	EnumTipoMetodoPago(Integer codigo) {
		this.codigo = codigo;

	}

}
