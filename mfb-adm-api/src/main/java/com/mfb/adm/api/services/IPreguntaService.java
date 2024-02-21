package com.mfb.adm.api.services;

import java.text.ParseException;

import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaPreguntas;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.comm.requests.SolicitudPregunta;

public interface IPreguntaService {

	RespuestaRest listarPorCriterios(SolicitudBusquedaPreguntas criterios) throws ParseException;
	
	RespuestaRest listarPaginado(SolicitudListaPaginada criterios);
	
	RespuestaRest preguntar(SolicitudPregunta dto, String username);

}
