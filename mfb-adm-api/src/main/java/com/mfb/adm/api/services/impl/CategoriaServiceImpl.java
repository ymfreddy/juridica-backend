package com.mfb.adm.api.services.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mfb.adm.api.services.ICategoriaService;
import com.mfb.adm.comm.constants.ConstAdm;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.CategoriaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCategorias;
import com.mfb.adm.core.entity.EntCategoria;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.repository.ICategoriaRepository;
import com.mfb.adm.core.repository.IEmpresaRepository;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	@Autowired
	private ICategoriaRepository repositorio;

	@Autowired
	private IEmpresaRepository repositorioEmpresa;

	@Override
	public RespuestaRest verPorId(Long id) {
		EntCategoria existe = repositorio.findById(id).orElse(null);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		CategoriaDto item = new ModelMapper().map(existe, CategoriaDto.class);
		cargarDatosAdicionales(item);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(item).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Categorias", allEntries = true)
	public RespuestaRest registrar(CategoriaDto dto) {
		EntEmpresa empresa = repositorioEmpresa.findById(dto.getIdEmpresa()).orElse(null);
		if (empresa == null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}
		EntCategoria existe = repositorio.findByNombreAndIdEmpresa(dto.getNombre(), dto.getIdEmpresa()).orElse(null);
		if (existe != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_CATEGORIAS_EXISTE_NOMBRE).build();
		}
		// insertar
		EntCategoria ent = new ModelMapper().map(dto, EntCategoria.class);
		ent.setId(null);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Categorias", allEntries = true)
	//@CacheEvict(value = "Categorias", key = "#dto.idEmpresa")
	//@CacheEvict(value = "Categorias", key = "#dto.idEmpresa")
	public RespuestaRest actualizar(CategoriaDto dto) {
		// validar codigo categoria existente
		EntCategoria ent = repositorio.findById(dto.getId()).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		// insertar
		ent.setNombre(dto.getNombre());
		ent.setImagenNombre(dto.getImagenNombre());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Categorias", allEntries = true)
	public RespuestaRest eliminar(Long id) {
		// validar codigo categoria existente
		EntCategoria ent = repositorio.findById(id).orElse(null);
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
	//@Cacheable(key = "#criterios.idEmpresa+':'+#criterios", value = "Categorias")
	//@Cacheable(key = "{#criterios.idEmpresa,#criterios}", value = "Categorias")
	@Cacheable(key = "#criterios", value = "Categorias")
	public RespuestaRest listarPorCriterios(SolicitudBusquedaCategorias criterios) {
		if (criterios.getIdEmpresa() == null || (long) criterios.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESA_ID_NO_ENVIADO).build();
		}

		List<EntCategoria> respuesta = repositorio.findAllByIdEmpresa(criterios.getIdEmpresa());
		List<CategoriaDto> lista = new ModelMapper().map(respuesta, new TypeToken<List<CategoriaDto>>() {
		}.getType());

		if (criterios.getIdsCategorias() != null && !criterios.getIdsCategorias().isEmpty()) {
			List<Long> listaIds = Arrays.asList(criterios.getIdsCategorias().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			lista = lista.stream().filter(x -> listaIds.contains(x.getId())).collect(Collectors.toList());
		}
		for (CategoriaDto item : lista) {
			cargarDatosAdicionales(item);
		}
		lista.sort(Comparator.comparing(CategoriaDto::getId).reversed());
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}	

	private void cargarDatosAdicionales(CategoriaDto dto) {
		// asignar ruta imagen
		if (dto.getImagenNombre() != null) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(ConstAdm.RUTA_DESCARGA)
					.path(dto.getImagenNombre()).toUriString();

			dto.setImagenRuta(
					fileDownloadUri.contains("localhost") || fileDownloadUri.contains("192.") ? fileDownloadUri
							: fileDownloadUri.replace("http://", "https://"));
		}
	}
}
