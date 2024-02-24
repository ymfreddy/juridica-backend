package com.mfb.adm.api.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.IUsuarioService;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.OpcionDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.dtos.SessionDto;
import com.mfb.adm.comm.dtos.UsuarioDto;
import com.mfb.adm.comm.dtos.UsuarioRegistroClienteDto;
import com.mfb.adm.comm.dtos.UsuarioRegistroDto;
import com.mfb.adm.comm.dtos.UsuarioResumenDto;
import com.mfb.adm.comm.enums.EnumTipoUsuario;
import com.mfb.adm.comm.requests.SolicitudBusquedaUsuarios;
import com.mfb.adm.comm.utils.Funciones;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.entity.EntUsuario;
import com.mfb.adm.core.query.IAdmQuery;
import com.mfb.adm.core.query.IUsuarioQuery;
import com.mfb.adm.core.repository.IEmpresaRepository;
import com.mfb.adm.core.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	@Autowired
	private IUsuarioRepository repositorio;

	@Autowired
	private IUsuarioQuery query;

	@Autowired
	private IEmpresaRepository repositorioEmpresa;

	@Autowired
	private IAdmQuery queryAdm;

	@Value("${nit.empresa}")
	private long NIT_EMPRESA;

	@Override
	@Transactional
	public RespuestaRest actualizarPassword(String username, String password, String user) {
		// validar existente
		EntUsuario ent = repositorio.findByUsername(username).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		// validar que el id del usuario sea el mismo de la actualizacion
		if (!ent.getUsername().equals(user)) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_UPDATE_PASSWORD_ERROR).build();
		}
		// actualizar
		ent.setPassword(password);
		ent.setCambiarClave(false);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	public RespuestaRest verPorId(Long id) {
		UsuarioDto existe = query.verPorId(id);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(existe).build();
	}

	@Override
	@Cacheable(key = "#criterios", value = "Usuarios")
	public RespuestaRest listarPorCriterios(SolicitudBusquedaUsuarios criterios) {
		if (criterios.getIdEmpresa() == null || (long) criterios.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}

		if (criterios.getResumen()) {
			List<UsuarioResumenDto> lista = query.listarResumenPorCriterios(criterios);
			return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
					.content(lista).build();
		}

		List<UsuarioDto> lista = query.listarPorCriterios(criterios);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Usuarios", allEntries = true)
	public RespuestaRest registrar(UsuarioRegistroDto dto) {
		// validar codigo cliente existente
		EntUsuario ent = repositorio.findByUsername(dto.getUsername()).orElse(null);
		if (ent != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_USARIOS_EXISTE_USERNAME).build();
		}

		if (dto.getOpciones() == null || dto.getOpciones().isEmpty()) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_USUARIO_OPCION_VACIO).build();
		}

		// insertar
		ent = new ModelMapper().map(dto, EntUsuario.class);
		ent.setId(null);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Usuarios", allEntries = true)
	public RespuestaRest registrarCliente(UsuarioRegistroClienteDto dto) {
		// validar codigo cliente existente
		EntUsuario ent = repositorio.findByEmail(dto.getEmail()).orElse(null);
		if (ent != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_USARIOS_EXISTE_EMAIL).build();
		}

		EntEmpresa empresa = repositorioEmpresa.findByNit(NIT_EMPRESA).orElse(null);
		// insertar
		String username= Funciones.generarCodigo(20);
		ent = new EntUsuario();
		ent.setId(null);
		ent.setCambiarClave(false);
		ent.setCelular(0);
		ent.setEmail(dto.getEmail());
		ent.setEnabled(true);
		ent.setIdEmpresa(empresa.getId());
		ent.setCodigoTipoUsuario(EnumTipoUsuario.EXTERNO.getValor());
		ent.setUsername(username);
		//ent.setPaterno(dto.getPaterno());
		//ent.setCi(dto.getCi());
		ent.setNombre(username);
		ent.setPassword(dto.getPassword());
		ent.setOpciones("3");
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();

	}

	@Override
	@Transactional
	@CacheEvict(value = "Usuarios", allEntries = true)
	public RespuestaRest actualizar(UsuarioRegistroDto dto) {
		// validar existente
		EntUsuario ent = repositorio.findById(dto.getId()).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		if (dto.getOpciones() == null || dto.getOpciones().isEmpty()) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_USUARIO_OPCION_VACIO).build();
		}

		// actualizar
		ent.setCambiarClave(dto.getCambiarClave());
		ent.setCelular(dto.getCelular());
		ent.setCi(dto.getCi());
		ent.setCodigoTipoUsuario(dto.getCodigoTipoUsuario());
		ent.setMaterno(dto.getMaterno());
		ent.setPaterno(dto.getPaterno());
		ent.setNombre(dto.getNombre());
		ent.setEmail(dto.getEmail());
		ent.setCambiarClave(dto.getCambiarClave());
		ent.setEnabled(dto.getEnabled());
		ent.setIdEmpresa(dto.getIdEmpresa());
		//ent.setIdSucursal(dto.getIdSucursal());
		ent.setOpciones(dto.getOpciones());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Usuarios", allEntries = true)
	public RespuestaRest eliminar(Long id) {
		// validar codigo cliente existente
		EntUsuario ent = repositorio.findById(id).orElse(null);
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
	@Cacheable(key = "{#root.method.name,#username}", value = "Usuarios")
	public RespuestaRest obtenerSession(String username) {
		UsuarioDto existe = query.verPorUsername(username);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		if (existe.getOpciones() == null || existe.getOpciones().isEmpty()) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_USUARIO_OPCION_ERROR).build();
		}

		SessionDto session = new SessionDto();
		session.setUsuario(existe);
		session.setOpciones(queryAdm.listarOpcionPorIds(existe.getOpciones()));
		return RespuestaRest.builder().success(true).content(session).build();
	}

	@Override
	public RespuestaRest listarOpciones() {
		List<OpcionDto> lista = queryAdm.listarOpcion();
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}
}
