package com.mfb.adm.core.query;

import java.util.List;

import com.mfb.adm.comm.dtos.OpcionDto;
import com.mfb.adm.comm.dtos.ParametricaDto;

public interface IAdmQuery {

	// administracion
	public List<ParametricaDto> listarParametricaPorTipo(String tipo);

	public ParametricaDto verParametricaPorId(long id);

	public List<OpcionDto> listarOpcion();

	public List<OpcionDto> listarOpcionPorIds(String ids);

}
