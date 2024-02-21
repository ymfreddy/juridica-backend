package com.mfb.adm.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.ParametricaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.api.services.IParametricaService;
import com.mfb.adm.core.query.IAdmQuery;

@Service
public class ParametricaServiceImpl implements IParametricaService {

	@Autowired
	private IAdmQuery repositorio;

	@Override
	@Cacheable(key = "#tipo", value = "Parametricas")
	public RespuestaRest listarPorTipo(String tipo) {
		
		List<ParametricaDto> lista = repositorio.listarParametricaPorTipo(tipo);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}

	@Override
	@Cacheable(key = "#id", value = "Parametricas")
	public RespuestaRest verPorId(Long id) {
		ParametricaDto dto = repositorio.verParametricaPorId(id);
		if (dto == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(dto).build();
	}

}
