package com.mfb.adm.api.services.impl;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.IRecursoService;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.RecursoDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.core.entity.EntRecurso;
import com.mfb.adm.core.repository.IRecursoRepository;

@Service
public class RecursoServiceImpl implements IRecursoService {

	@Autowired
	private IRecursoRepository repositorio;

	@Override
	public RespuestaRest listar() {
		List<EntRecurso> respuesta = repositorio.findAll();
		List<RecursoDto> lista = new ModelMapper().map(respuesta, new TypeToken<List<RecursoDto>>() {
		}.getType());
		lista.sort(Comparator.comparing(RecursoDto:: getId).reversed());
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}
}
