package com.mfb.adm.api.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.ICitaService;
import com.mfb.adm.api.services.ICorrelativoService;
import com.mfb.adm.comm.constants.ConstApp;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.CitaDto;
import com.mfb.adm.comm.dtos.CitaEstadoDto;
import com.mfb.adm.comm.dtos.CitaResumenDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCitas;
import com.mfb.adm.core.entity.EntCita;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.query.ICitaQuery;
import com.mfb.adm.core.repository.ICitaRepository;
import com.mfb.adm.core.repository.IEmpresaRepository;

@Service
public class CitaServiceImpl implements ICitaService {
	@Autowired
	private ICitaRepository repositorio;

	@Autowired
	private ICitaQuery query;
	
	@Autowired
	private ICorrelativoService serviceCorrelativo;
	
	@Autowired
	private IEmpresaRepository repositorioEmpresa;

	@Override
	public RespuestaRest verPorId(String id) {
		UUID uuid = UUID.fromString(id);
		CitaDto existe = query.verPorId(uuid);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(existe).build();
	}

	@Override
	@Cacheable(key = "#criterios", value = "Citas")
	public RespuestaRest listarPorCriterios(SolicitudBusquedaCitas criterios) {
		if (criterios.getIdEmpresa() == null || (long) criterios.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}

		if (criterios.getResumen()) {
			List<CitaResumenDto> lista = query.listarResumenPorCriterios(criterios);
			return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
					.content(lista).build();
		}
		
		List<CitaDto> lista = query.listarPorCriterios(criterios);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}
	
	@Override
	public RespuestaRest listarEstados() {
		List<CitaEstadoDto> lista = query.listarEstados();
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Citas", allEntries = true)
	public RespuestaRest registrar(CitaDto dto)  throws ParseException {
		EntEmpresa empresa = repositorioEmpresa.getById(dto.getIdEmpresa());
		// validaciones
		if (empresa == null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}

		// insertar
		Long correlativo = serviceCorrelativo.generar(dto.getIdEmpresa(), ConstApp.OPERACION_CITA, new Date());
	
		String fechaAux=dto.getInicio().substring(0,10);
		Date fecha = ConstApp.FORMATO_FECHA_ISO.parse(fechaAux);
		EntCita ent = new EntCita();
		ent.setIdEmpresa(dto.getIdEmpresa());
		ent.setIdCliente(dto.getIdCliente());
		ent.setCorrelativo(correlativo);
		ent.setCodigoEstado(dto.getCodigoEstado());
		ent.setFecha(fecha);
		ent.setInicio(dto.getInicio());
		ent.setFin(dto.getFin());
		ent.setCodigoTipo(dto.getCodigoTipo());
		ent.setNota(dto.getNota());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Citas", allEntries = true)
	public RespuestaRest actualizar(CitaDto dto) throws ParseException {
		// validar existente
		UUID uuid = UUID.fromString(dto.getId());
		EntCita ent = repositorio.findById(uuid).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		String fechaAux=dto.getInicio().substring(0,10);
		Date fecha = ConstApp.FORMATO_FECHA_ISO.parse(fechaAux);
		// actualizar
		ent.setIdCliente(dto.getIdCliente());
		ent.setFecha(fecha);
		ent.setFin(dto.getFin());
		ent.setInicio(dto.getInicio());
		ent.setCodigoEstado(dto.getCodigoEstado());
		ent.setCodigoTipo(dto.getCodigoTipo());
		ent.setNota(dto.getNota());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "Citas", allEntries = true)
	public RespuestaRest actualizarFecha(CitaDto dto) throws ParseException {
		// validar existente
		UUID uuid = UUID.fromString(dto.getId());
		EntCita ent = repositorio.findById(uuid).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		String fechaAux=dto.getInicio().substring(0,10);
		Date fecha = ConstApp.FORMATO_FECHA_ISO.parse(fechaAux);
		// actualizar
		ent.setFecha(fecha);
		ent.setFin(dto.getFin());
		ent.setInicio(dto.getInicio());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Citas", allEntries = true)
	public RespuestaRest eliminar(String id) {
		UUID uuid = UUID.fromString(id);
		// validar codigo cliente existente
		EntCita ent = repositorio.findById(uuid).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		// eliminar
		ent.setActive(false);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_DELETE_SUCCESSFULLY).content(ent.getId())
				.build();
	}
}
