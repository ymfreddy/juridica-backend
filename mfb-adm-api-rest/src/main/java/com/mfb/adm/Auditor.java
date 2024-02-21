package com.mfb.adm;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auditor implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Optional<String> user =SecurityContextHolder.getContext().getAuthentication()!=null				
				?  Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().split(":")[0])
				:  Optional.ofNullable("ENLINEA");
		return user;
	}

	public static String getUsuario() {
		String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().split(":")[0];
		return user;
	}

	public static Long getNitEmpresa() {
		Long nit = Long.parseLong(
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().split(":")[1]);
		return nit;
	}
}