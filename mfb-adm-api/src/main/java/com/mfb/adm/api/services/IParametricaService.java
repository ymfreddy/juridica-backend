package com.mfb.adm.api.services;

import com.mfb.adm.comm.dtos.RespuestaRest;

public interface IParametricaService {

	RespuestaRest listarPorTipo(String tipo);

	RespuestaRest verPorId(Long id);

}
