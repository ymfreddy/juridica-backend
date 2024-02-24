package com.mfb.adm.api.services;

import java.text.ParseException;

import com.mfb.adm.comm.dtos.CitaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCitas;

public interface ICitaService {

	RespuestaRest registrar(CitaDto dto) throws ParseException;

	RespuestaRest actualizar(CitaDto dto) throws ParseException;
	
	RespuestaRest actualizarFecha(CitaDto dto) throws ParseException;

	RespuestaRest eliminar(String id);

	RespuestaRest verPorId(String id);

	RespuestaRest listarPorCriterios(SolicitudBusquedaCitas criterios);
	
	RespuestaRest listarEstados();
}
