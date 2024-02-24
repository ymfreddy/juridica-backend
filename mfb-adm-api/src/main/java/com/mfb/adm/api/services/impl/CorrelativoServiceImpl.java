package com.mfb.adm.api.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.ICorrelativoService;
import com.mfb.adm.comm.constants.ConstApp;
import com.mfb.adm.core.entity.EntCorrelativo;
import com.mfb.adm.core.repository.ICorrelativoRepository;

@Service
public class CorrelativoServiceImpl implements ICorrelativoService {

	@Autowired
	private ICorrelativoRepository repositorio;

	@Override
	public Long generar(Long idEmpresa, String operacion, Date fecha) {
		short gestion = Short.parseShort(ConstApp.FORMATO_GESTION.format(fecha));
		EntCorrelativo existe = repositorio.findByIdEmpresaAndGestionAndOperacion(idEmpresa, gestion, operacion).orElse(null);
		if (existe == null) {
			existe = new EntCorrelativo();
			existe.setIdEmpresa(idEmpresa);
			existe.setGestion(gestion);
			existe.setOperacion(operacion);
			existe.setSecuencia(1L);
		} else {
			existe.setSecuencia(existe.getSecuencia() + 1);
		}
		repositorio.save(existe);
		return existe.getSecuencia();
	}
}
