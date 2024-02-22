package com.mfb.adm.api.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.IClienteService;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.ClienteDto;
import com.mfb.adm.comm.dtos.ClienteResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaClientes;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.core.entity.EntCliente;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.query.IClienteQuery;
import com.mfb.adm.core.repository.IClienteRepository;
import com.mfb.adm.core.repository.IEmpresaRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository repositorio;

	@Autowired
	private IEmpresaRepository repositorioEmpresa;

	@Autowired
	private IClienteQuery queryCliente;

	@Override
	// @Cacheable(key = "#id", value = "Clientes")
	public RespuestaRest verPorId(Long id) {
		ClienteDto existe = queryCliente.verPorId(id);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(existe).build();
	}

	@Override
	// @Cacheable(key = "{ #nitEmpresa, #codigoCliente }", value = "Clientes")
	public RespuestaRest verTelefonoPorIdEmpresaYCodigo(Long idEmpresa, String codigoCliente) {
		String existe = queryCliente.verTelefonoPorIdEmpresaYCodigo(idEmpresa, codigoCliente);
		if (existe == null || existe.isEmpty()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(existe).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Clientes", allEntries = true)
	public RespuestaRest registrar(ClienteDto dto) {
		EntEmpresa empresa = repositorioEmpresa.findById(dto.getIdEmpresa()).orElse(null);
		if (empresa == null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}
		// validar codigo cliente existente
		EntCliente ent = repositorio.findByCodigoClienteAndIdEmpresa(dto.getCodigoCliente(), dto.getIdEmpresa())
				.orElse(null);
		if (ent != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_CLIENTES_EXISTE_CODIGO + dto.getCodigoCliente())
					.build();
		}
		// insertar
		ent = new ModelMapper().map(dto, EntCliente.class);
		ent.setId(null);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Clientes", allEntries = true)
	public RespuestaRest actualizar(ClienteDto dto) {
		// validar codigo cliente existente
		EntCliente ent = repositorio.findById(dto.getId()).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		// validar codigo cliente existente
		EntCliente existe = repositorio.findByCodigoClienteAndIdEmpresa(dto.getCodigoCliente(), dto.getIdEmpresa())
				.orElse(null);
		if (existe != null && !existe.getId().equals(dto.getId())) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_CLIENTES_EXISTE_CODIGO + dto.getCodigoCliente())
					.build();
		}
		// insertar
		ent.setCodigoCliente(dto.getCodigoCliente());
		ent.setCodigoTipoDocumentoIdentidad(dto.getCodigoTipoDocumentoIdentidad());
		ent.setComplemento(dto.getComplemento());
		ent.setDireccion(dto.getDireccion());
		ent.setEmail(dto.getEmail());
		ent.setNombre(dto.getNombre());
		ent.setNumeroDocumento(dto.getNumeroDocumento());
		ent.setTelefono(dto.getTelefono());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Clientes", allEntries = true)
	public RespuestaRest eliminar(Long id) {
		// validar codigo cliente existente
		EntCliente ent = repositorio.findById(id).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		// eliminar
		ent.setActive(false);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_DELETE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Cacheable(key = "#criterios", value = "Clientes")
	public RespuestaRest listarPorCriterios(SolicitudBusquedaClientes criterios) {
		if (criterios.getIdEmpresa() == null || (long) criterios.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}

		if (criterios.getResumen()) {
			List<ClienteResumenDto> lista = queryCliente.listarResumenPorCriterios(criterios);
			return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
					.content(lista).build();
		}

		List<ClienteDto> lista = queryCliente.listarPorCriterios(criterios);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}

	@Override
	@Cacheable(key = "#paginacion", value = "Clientes")
	public RespuestaRest listarPaginado(SolicitudListaPaginada paginacion) {
		if (paginacion.getIdEmpresa() == null || (long) paginacion.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}

		if (paginacion.getCantidadItems() <= 0 || paginacion.getPagina() <= 0) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_PAGE_REQUEST_ERROR).build();
		}

		RespuestaListaPaginada lista = queryCliente.listarPaginado(paginacion);
		return RespuestaRest.builder().success(true)
				.message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.getTotalItems())).content(lista).build();
	}

}
