package com.mfb.adm.core.query;

import java.util.List;

import com.mfb.adm.comm.dtos.UsuarioDto;
import com.mfb.adm.comm.dtos.UsuarioResumenDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaUsuarios;

public interface IUsuarioQuery {

	public UsuarioDto verPorUsername(String username);
	
	public UsuarioDto verPorId(Long id);

	public List<UsuarioDto> listarPorCriterios(SolicitudBusquedaUsuarios criterios);
	
	public List<UsuarioResumenDto> listarResumenPorCriterios(SolicitudBusquedaUsuarios criterios);

}
