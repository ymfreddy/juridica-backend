package com.mfb.adm.core.query;

import java.util.List;
import java.util.UUID;

import com.mfb.adm.comm.dtos.CitaDto;
import com.mfb.adm.comm.dtos.CitaEstadoDto;
import com.mfb.adm.comm.dtos.CitaResumenDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaCitas;

public interface ICitaQuery {

	public CitaDto verPorId(UUID id);

	public List<CitaDto> listarPorCriterios(SolicitudBusquedaCitas criterios);
	
	public List<CitaResumenDto> listarResumenPorCriterios(SolicitudBusquedaCitas criterios);
	
	public List<CitaEstadoDto> listarEstados();
	
}
