package com.mfb.adm.api.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfb.adm.api.restTemplate.IChatPdfService;
import com.mfb.adm.api.services.ILimiteService;
import com.mfb.adm.api.services.IPreguntaService;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.PreguntaDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.dtos.RespuestaRestChatPdf;
import com.mfb.adm.comm.dtos.RespuestaRestTemplate;
import com.mfb.adm.comm.requests.SolicitudBusquedaPreguntas;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.comm.requests.SolicitudPregunta;
import com.mfb.adm.core.entity.EntPregunta;
import com.mfb.adm.core.entity.EntRecurso;
import com.mfb.adm.core.query.IPreguntaQuery;
import com.mfb.adm.core.repository.IPreguntaRepository;
import com.mfb.adm.core.repository.IRecursoRepository;

@Service
public class PreguntaServiceImpl implements IPreguntaService {

	@Autowired
	private IPreguntaRepository repositorio;
	
	@Autowired
	private IPreguntaQuery query;
	
	@Autowired
	private IRecursoRepository repositorioRecurso;
	
	@Autowired
	private IChatPdfService servicioChatPdf;	

	@Autowired
	private ILimiteService servicioLimite;
	
	private final static int CANTIDAD_PREGUNTAS=30;
	@Override
	// @Transactional
	// @CacheEvict(value = "Preguntas", allEntries = true)
	public RespuestaRest preguntar(SolicitudPregunta dto, String username) {
		// validar codigo cliente existente
		EntRecurso existe = repositorioRecurso.findById(dto.getIdRecurso()).orElse(null);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_RECURSOS_NO_EXISTE + dto.getIdRecurso()).build();
		}

		if (existe.getSourceId() == null || existe.getSourceId().isEmpty()) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_RECURSOS_NO_EXISTE_CHATPDF + existe.getNombre()).build();
		}

		// verificar limite
		int limite = servicioLimite.contar(username, new Date()); 
		if (limite>CANTIDAD_PREGUNTAS) {
			return RespuestaRest.builder().message(String.format(MsgAdm.RESPONSE_LIMITE_EXCEDIDO , CANTIDAD_PREGUNTAS)).build();
		}		
		
		EntPregunta entidad = new EntPregunta();
		entidad.setFecha(new Date());
		entidad.setPregunta(dto.getPregunta());
		entidad.setRecursos(dto.getIdRecurso().toString());
		entidad.setUsername(username);
		entidad = repositorio.save(entidad);
		
		RespuestaRestTemplate respuestaServicioChatPdf = servicioChatPdf.preguntar(dto.getPregunta(), existe.getSourceId());
		if (respuestaServicioChatPdf.isSuccess()) {
			RespuestaRestChatPdf respuestaChat = (RespuestaRestChatPdf)respuestaServicioChatPdf.getContent();
			entidad.setRespuesta(respuestaChat.getContent());
			repositorio.save(entidad);
		}
		
		PreguntaDto pregunta = new ModelMapper().map(entidad, PreguntaDto.class);
		
		return RespuestaRest.builder().success(respuestaServicioChatPdf.isSuccess()).message(respuestaServicioChatPdf.getMessage())
				.content(respuestaServicioChatPdf.isSuccess() ? pregunta : null).build();
	}

	@Override
	public RespuestaRest listarPaginado(SolicitudListaPaginada paginacion) {
		if (paginacion.getUsername() == null || paginacion.getUsername().isEmpty()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_USERNAME_ERROR).build();
		}
		
		if (paginacion.getCantidadItems() <=0 || paginacion.getPagina() <= 0) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_PAGE_REQUEST_ERROR).build();
		}

     	RespuestaListaPaginada lista = query.listarPaginado(paginacion);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.getTotalItems()))
				.content(lista).build();
	}

	@Override
	public RespuestaRest listarPorCriterios(SolicitudBusquedaPreguntas criterios) throws ParseException {
		if (criterios.getUsername() == null || criterios.getUsername().isEmpty()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_USERNAME_ERROR).build();
		}

		List<PreguntaDto> lista = query.listarPorCriterios(criterios);
		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}
}
