package com.mfb.adm.core.query.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.dtos.CitaDto;
import com.mfb.adm.comm.dtos.CitaEstadoDto;
import com.mfb.adm.comm.dtos.CitaResumenDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaCitas;
import com.mfb.adm.core.query.ICitaQuery;

@Repository
public class CitaQueryImpl implements ICitaQuery {

	private final String SQL_SELECT_CITA = "select * FROM adm.v_citas ";
	private final String SQL_SELECT_CITA_ESTADOS = "select codigo, nombre, color FROM adm.citas_estados where active ";
	private final String SQL_SELECT_CITA_RESUMEN = "select id, correlativo, inicio, fin, color, descripcion  FROM adm.v_citas ";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public CitaDto verPorId(UUID idCita) {
		String sql = SQL_SELECT_CITA + " where id=:id ";
		RowMapper<CitaDto> vRowMapper = new BeanPropertyRowMapper<CitaDto>(CitaDto.class);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", idCita);
		List<CitaDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override
	public List<CitaEstadoDto> listarEstados() {
		String sql = SQL_SELECT_CITA_ESTADOS;
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<CitaEstadoDto> vRowMapper = new BeanPropertyRowMapper<CitaEstadoDto>(CitaEstadoDto.class);
		List<CitaEstadoDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}
	
	@Override
	public List<CitaDto> listarPorCriterios(SolicitudBusquedaCitas criterios) {
		String sql = SQL_SELECT_CITA + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<CitaDto> vRowMapper = new BeanPropertyRowMapper<CitaDto>(CitaDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getCorrelativo() != null && criterios.getCorrelativo()> 0L) {
			sql += " and correlativo=:correlativo ";
			parameters.addValue("correlativo", criterios.getCorrelativo());
		}
		if (criterios.getCodigoCliente() != null && !criterios.getCodigoCliente().isEmpty()) {
			sql += " and codigo_cliente=:codigo_cliente ";
			parameters.addValue("codigo_cliente", criterios.getCodigoCliente());
		}
		if (criterios.getTiposCita() != null && !criterios.getTiposCita().isEmpty()) {
			sql += " and tipo in (:tipo_cita)";
			List<Long> listaIds = Arrays.asList(criterios.getTiposCita().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("tipo_cita", listaIds);
		}
		if (criterios.getEstadosCita() != null && !criterios.getEstadosCita().isEmpty()) {
			sql += " and estado_cita in (:estado_cita)";
			List<Long> listaIds = Arrays.asList(criterios.getEstadosCita().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("estado_cita", listaIds);
		}

		sql += " order by correlativo desc ";
		
		List<CitaDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}
	
	@Override
	public List<CitaResumenDto> listarResumenPorCriterios(SolicitudBusquedaCitas criterios) {
		String sql = SQL_SELECT_CITA_RESUMEN + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<CitaResumenDto> vRowMapper = new BeanPropertyRowMapper<CitaResumenDto>(CitaResumenDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getCorrelativo() != null && criterios.getCorrelativo()> 0L) {
			sql += " and correlativo=:correlativo ";
			parameters.addValue("correlativo", criterios.getCorrelativo());
		}
		if (criterios.getCodigoCliente() != null && !criterios.getCodigoCliente().isEmpty()) {
			sql += " and codigo_cliente=:codigo_cliente ";
			parameters.addValue("codigo_cliente", criterios.getCodigoCliente());
		}
		if (criterios.getTiposCita() != null && !criterios.getTiposCita().isEmpty()) {
			sql += " and tipo in (:tipo_cita)";
			List<Long> listaIds = Arrays.asList(criterios.getTiposCita().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("tipo_cita", listaIds);
		}
		if (criterios.getEstadosCita() != null && !criterios.getEstadosCita().isEmpty()) {
			sql += " and estado_cita in (:estado_cita)";
			List<Long> listaIds = Arrays.asList(criterios.getEstadosCita().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("estado_cita", listaIds);
		}

		sql += " order by correlativo desc ";
		
		List<CitaResumenDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}
}
