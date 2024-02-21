package com.mfb.adm.api.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.ILimiteService;
import com.mfb.adm.comm.constants.ConstApp;
import com.mfb.adm.core.entity.EntLimite;
import com.mfb.adm.core.repository.ILimiteRepository;

@Service
public class LimiteServiceImpl implements ILimiteService {

	@Autowired
	private ILimiteRepository repositorio;

	@Override
	public Integer contar(String username, Date fecha) {
		String fechaAux = ConstApp.FORMATO_FECHA.format(fecha);
		EntLimite existe = repositorio.findByUsernameAndDia(username, fechaAux).orElse(null);
		if (existe == null) {
			existe = new EntLimite();
			existe.setUsername(username);
			existe.setDia(fechaAux);
			existe.setCantidad(1);
		} else {
			existe.setCantidad(existe.getCantidad() + 1);
		}
		repositorio.save(existe);
		return existe.getCantidad();
	}
}
