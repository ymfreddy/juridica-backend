package com.mfb.adm.core.query.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.constants.ConstApp;
import com.mfb.adm.comm.dtos.PreguntaDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaPreguntas;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.comm.utils.Funciones;
import com.mfb.adm.core.query.IPreguntaQuery;

@Repository
public class PreguntaQueryImpl implements IPreguntaQuery {

	private final String SQL_SELECT_PREGUNTAS = "select * FROM adm.v_preguntas ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginada solicitud) {
		String sql = SQL_SELECT_PREGUNTAS + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<PreguntaDto> vRowMapper = new BeanPropertyRowMapper<PreguntaDto>(PreguntaDto.class);
		if (solicitud.getUsername() != null && !solicitud.getUsername().isEmpty()) {
			sql += " username=:username ";
			parameters.addValue("username", solicitud.getUsername());
		}
		if (solicitud.getFiltro() != null || !solicitud.getFiltro().isEmpty()) {
			sql += " and (pregunta ilike :terminoFinal or respuesta ilike :terminoFinal )";
			String terminoFinal = "%" + solicitud.getFiltro().trim() + "%";
			parameters.addValue("terminoFinal", terminoFinal);
		}

		RespuestaListaPaginada respuesta = new RespuestaListaPaginada();
		Object total = jdbcTemplate.queryForObject(sql.replace("*", "COUNT(0)"), parameters, Object.class);

		if (solicitud.getCampoOrden() == null || solicitud.getCampoOrden().isEmpty()) {
			sql += " order by fecha desc";
		} else {
			sql += " order by " + Funciones.camelToSnake(solicitud.getCampoOrden())	+ (solicitud.getTipoOrden() == -1 ? " desc " : " asc ");
		}

		respuesta.setTotalItems(Integer.parseInt(total.toString()));
		respuesta.setPaginaActual(solicitud.getPagina());
		respuesta.setPaginasTotales(respuesta.getTotalItems() / solicitud.getCantidadItems());
		Integer ofset = (solicitud.getPagina() - 1) * solicitud.getCantidadItems();
		sql += " LIMIT :cantidad_registros OFFSET :ofset ";
		parameters.addValue("cantidad_registros", solicitud.getCantidadItems());
		parameters.addValue("ofset", ofset);
		List<PreguntaDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		respuesta.setItems(lista);
		return respuesta;
	}

	@Override
	public List<PreguntaDto> listarPorCriterios(SolicitudBusquedaPreguntas criterios) throws ParseException {
		String sql = SQL_SELECT_PREGUNTAS + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<PreguntaDto> vRowMapper = new BeanPropertyRowMapper<PreguntaDto>(PreguntaDto.class);
		if (criterios.getUsername() != null && !criterios.getUsername().isEmpty()) {
			sql += " username=:username ";
			parameters.addValue("username", criterios.getUsername());
		}
		
		if (criterios.getFechaInicio() != null && criterios.getFechaFin() != null) {
			sql += " and fecha between :fecha_inicio and :fecha_fin  ";
			parameters.addValue("fecha_inicio",
					ConstApp.FORMATO_FECHA_HORA_COMPLETA.parse(criterios.getFechaInicio() + " 00:00:00"));
			parameters.addValue("fecha_fin",
					ConstApp.FORMATO_FECHA_HORA_COMPLETA.parse(criterios.getFechaFin() + " 23:59:59"));
		}
		
		sql += " order by fecha desc";

		List<PreguntaDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

}
