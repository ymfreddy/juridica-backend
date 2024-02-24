package com.mfb.adm.comm.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumTipoUsuario {
	SUPER_USUARIO("USP"), ADMINISTRADOR("UADM"), MEDICO("UMED"), ASISTENTE("UASIS"), EXTERNO("UEXT");

	private String valor;

	private EnumTipoUsuario(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static List<String> getRoles(String valor) {
		if (valor.equals("USP")) {
			return Arrays.asList("ROLE_SUPERADMIN");
		}
		if (valor.equals("UADM")) {
			return Arrays.asList("ROLE_ADMIN");
		}
		if (valor.equals("UMED")) {
			return Arrays.asList("ROLE_MEDICO");
		}
		if (valor.equals("UASIS")) {
			return Arrays.asList("ROLE_ASISTENTE");
		}
		if (valor.equals("UEXT")) {
			return Arrays.asList("ROLE_EXTERNO");
		}

		return new ArrayList<>();
	}
}
