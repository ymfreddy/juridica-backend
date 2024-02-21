package com.mfb.adm.comm.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumTipoUsuario {
	SUPER_USUARIO(1), ADMINISTRADOR(2), OPERADOR(3), FACTURACION(4), ASESOR(5), CLIENTE_USUARIO(6);
	private Integer valor;

	private EnumTipoUsuario(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	public static List<String> getRoles(Integer valor) {
		if(valor==1) {
			return Arrays.asList("ROLE_SUPERADMIN");
		}
		if(valor==2) {
			return Arrays.asList("ROLE_ADMIN");
		}
		if(valor==3) {
			return Arrays.asList("ROLE_OPERADOR");
		}
		if(valor==4) {
			return Arrays.asList("ROLE_FACTURACION");
		}
		if(valor==5) {
			return Arrays.asList("ROLE_ASESOR");
		}
		if(valor==6) {
			return Arrays.asList("ROLE_CLIENTE_USUARIO");
		}
		
		return new ArrayList<>();
	}
}
