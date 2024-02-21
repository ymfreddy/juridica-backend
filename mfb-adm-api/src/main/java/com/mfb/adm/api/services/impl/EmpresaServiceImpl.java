package com.mfb.adm.api.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.services.IEmpresaService;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.EmpresaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.repository.IEmpresaRepository;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

	@Autowired
	private IEmpresaRepository repositorio;

	@Value("${ruta.logo}")
	private String URL_LOGOS;

	@Override
	@Cacheable(key = "{#root.method.name,#id}", value = "Empresas")
	public RespuestaRest verPorId(Long id) {
		EntEmpresa existe = repositorio.findById(id).orElse(null);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY)
				.content(new ModelMapper().map(existe, EmpresaDto.class)).build();
	}

	@Override
	@Cacheable(key = "{#root.method.name,#nit}", value = "Empresas")
	public RespuestaRest verPorNit(Long nit) {
		EntEmpresa existe = repositorio.findByNit(nit).orElse(null);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY)
				.content(new ModelMapper().map(existe, EmpresaDto.class)).build();
	}

	@Override
	@Cacheable("Empresas")
	public RespuestaRest listar() {
		List<EntEmpresa> respuesta = repositorio.findAll();
		List<EmpresaDto> lista = new ModelMapper().map(respuesta, new TypeToken<List<EmpresaDto>>() {
		}.getType());
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Empresas", allEntries = true)
	public RespuestaRest registrar(EmpresaDto dto) {
		// validar codigo cliente existente
		EntEmpresa existe = repositorio.findByNit(dto.getNit()).orElse(null);
		if (existe != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_EXISTE_NIT + dto.getNit()).build();
		}

		// validar si existe sigla
		existe = repositorio.findBySigla(dto.getSigla()).orElse(null);
		if (existe != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_EXISTE_SIGLA + dto.getSigla()).build();
		}

		// insertar
		EntEmpresa ent = new ModelMapper().map(dto, EntEmpresa.class);
		long idGenerado = (int) (Math.random() * ((100000 - 10000) + 1)) + 10000;
		ent.setId(idGenerado);
		ent = repositorio.save(ent);

		// copiar logo de 5571909 a nuevo nit
		String fromFile = URL_LOGOS + "5571909017.png";
		String toFile = URL_LOGOS + dto.getNit() + ".png";
		this.copyFileNIO(fromFile, toFile);

		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Empresas", allEntries = true)
	public RespuestaRest actualizar(EmpresaDto dto) {
		// validar codigo cliente existente
		EntEmpresa ent = repositorio.findById(dto.getId()).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		// validar nit
		EntEmpresa existe = repositorio.findByNit(dto.getNit()).orElse(null);
		if (existe != null && !existe.getId().equals(dto.getId())) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_EXISTE_NIT + dto.getNit()).build();
		}

		// verificar si existe algun correlativo generado
		existe = repositorio.findBySigla(dto.getSigla()).orElse(null);
		if (existe != null && !existe.getId().equals(dto.getId())) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_EXISTE_SIGLA + dto.getSigla()).build();
		}

		// insertar
		ent.setNit(dto.getNit());
		ent.setSigla(dto.getSigla());
		ent.setNombre(dto.getNombre());
		ent.setRepresentanteLegal(dto.getRepresentanteLegal());
		ent.setSigla(dto.getSigla());
		ent.setEmail(dto.getEmail());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Empresas", allEntries = true)
	public RespuestaRest eliminar(Long id) {
		// validar codigo cliente existente
		EntEmpresa ent = repositorio.findById(id).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}

		// eliminar
		ent.setActive(false);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_DELETE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	private void copyFileNIO(String from, String to) {
		try {
			Path fromFile = Paths.get(from);
			Path toFile = Paths.get(to);

			// if fromFile doesn't exist, Files.copy throws NoSuchFileException
			if (Files.notExists(fromFile)) {
				System.out.println("File doesn't exist? " + fromFile);
				return;
			}

			// if toFile folder doesn't exist, Files.copy throws NoSuchFileException
			// if toFile parent folder doesn't exist, create it.
			Path parent = toFile.getParent();
			if (parent != null) {
				if (Files.notExists(parent)) {
					Files.createDirectories(parent);
				}
			}

			Files.copy(fromFile, toFile);
		} catch (Exception e) {
			System.out.println("error al copiar el archivo " + e.getMessage());
		}
	}
}
