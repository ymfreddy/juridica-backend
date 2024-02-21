package com.mfb.adm.core.query.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.dtos.OpcionDto;
import com.mfb.adm.comm.dtos.ParametricaDto;
import com.mfb.adm.core.query.IAdmQuery;

@Repository
public class AdmQueryImpl implements IAdmQuery {

	private final String SQL_SELECT_PARAMETRICA = "select id, nombre, descripcion, valor, tipo from adm.parametricas where active is true ";
	private final String SQL_SELECT_OPCION = "select id, titulo, ruta, icono, estilo, grupo, orden, descripcion from adm.opciones WHERE active order by grupo, orden";
	private final String SQL_SELECT_OPCION_POR_IDS = "select id, titulo, ruta, icono, estilo, grupo, orden, descripcion from adm.opciones where active and id in (:ids) order by grupo, orden";
	//private final String SQL_SELECT_SISTEMA = "SELECT id, nombre, version_sistema, tipo_sistema, modalidad, codigo_documento_sector FROM sfe.sistemas where active = true";
	//private final String SQL_SELECT_ASOCIACION_RESUMEN = "select id, nombre_sistema, modalidad, ambiente, codigo_asociacion, codigo_documento_sector, documento_sector, conexion_automatica FROM sfe.v_asociaciones ";
	private final String SQL_SELECT_ASOCIACION_RESUMEN_CODIGOS = "select id, nombre_sistema, modalidad, ambiente, codigo_asociacion, codigo_documento_sector, documento_sector, conexion_automatica FROM sfe.v_asociaciones where codigo_asociacion in (:codigos) order by nombre_sistema ";
	private final String SQL_SELECT_ASOCIACION_RESUMEN_POR_NIT = "select id, nombre_sistema, modalidad, ambiente, codigo_asociacion, codigo_documento_sector, documento_sector, conexion_automatica FROM sfe.v_asociaciones where nit=:nit order by nombre_sistema";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public ParametricaDto verParametricaPorId(long id) {
		RowMapper<ParametricaDto> vRowMapper = new BeanPropertyRowMapper<ParametricaDto>(ParametricaDto.class);
		String query = SQL_SELECT_PARAMETRICA + " and id=:id";
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
		List<ParametricaDto> lista = jdbcTemplate.query(query, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override	
	public List<ParametricaDto> listarParametricaPorTipo(String tipo) {
		System.out.println("Entra a bd "+tipo);
		RowMapper<ParametricaDto> vRowMapper = new BeanPropertyRowMapper<ParametricaDto>(ParametricaDto.class);
		String query = tipo.equals("*") ? SQL_SELECT_PARAMETRICA + " order by tipo, nombre "
				: SQL_SELECT_PARAMETRICA + " and tipo=:tipo order by nombre ";
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("tipo", tipo);
		List<ParametricaDto> lista = tipo.equals("*") ? this.jdbcTemplate.query(query, vRowMapper)
				: this.jdbcTemplate.query(query, parameters, vRowMapper);
		return lista;
	}

	@Override
	public List<OpcionDto> listarOpcionPorIds(String ids){
		RowMapper<OpcionDto> vRowMapper = new BeanPropertyRowMapper<OpcionDto>(OpcionDto.class);
		List<Long> listaIds = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", listaIds);
		List<OpcionDto> lista = this.jdbcTemplate.query(SQL_SELECT_OPCION_POR_IDS, parameters, vRowMapper);
		return lista;
	}

	@Override
	public List<OpcionDto> listarOpcion() {
		RowMapper<OpcionDto> vRowMapper = new BeanPropertyRowMapper<OpcionDto>(OpcionDto.class);
		List<OpcionDto> lista = this.jdbcTemplate.query(SQL_SELECT_OPCION, vRowMapper);
		return lista;
	}

}
