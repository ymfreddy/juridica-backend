package com.mfb.adm.core.query;

import java.text.ParseException;
import java.util.List;

import com.mfb.adm.comm.dtos.PreguntaDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaPreguntas;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;

public interface IPreguntaQuery {

	public List<PreguntaDto> listarPorCriterios(SolicitudBusquedaPreguntas criterios) throws ParseException;
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginada criterios);

}
