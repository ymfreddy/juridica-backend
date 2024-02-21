package com.mfb.adm.core.query.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.dtos.UsuarioDto;
import com.mfb.adm.comm.dtos.UsuarioResumenDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaUsuarios;
import com.mfb.adm.core.query.IUsuarioQuery;

@Repository
public class UsuarioQueryImpl implements IUsuarioQuery {

	private final String SQL_SELECT_USUARIO = "select * FROM adm.v_usuarios ";
	private final String SQL_SELECT_USUARIO_RESUMEN = "select id, nombre_completo, username FROM adm.v_usuarios ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;


	@Override
	public UsuarioDto verPorUsername(String username) {
		String sql = SQL_SELECT_USUARIO + " where username=:username ";
		RowMapper<UsuarioDto> vRowMapper = new BeanPropertyRowMapper<UsuarioDto>(UsuarioDto.class);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);
		List<UsuarioDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}
	
	@Override
	public UsuarioDto verPorId(Long idUsuario) {
		String sql = SQL_SELECT_USUARIO + " where id=:id ";
		RowMapper<UsuarioDto> vRowMapper = new BeanPropertyRowMapper<UsuarioDto>(UsuarioDto.class);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", idUsuario);
		List<UsuarioDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override
	public List<UsuarioDto> listarPorCriterios(SolicitudBusquedaUsuarios criterios) {
		String sql = SQL_SELECT_USUARIO + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<UsuarioDto> vRowMapper = new BeanPropertyRowMapper<UsuarioDto>(UsuarioDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getIdTipoUsuario() != null && (long) criterios.getIdTipoUsuario() > 0) {
			sql += " and id_tipo_usuario=:id_tipo_usuario ";
			parameters.addValue("id_tipo_usuario", criterios.getIdTipoUsuario());
		}
		
		if (criterios.getUsername() != null && !criterios.getUsername().isEmpty()) {
			sql += " and username=:username ";
			parameters.addValue("username", criterios.getUsername());
		}

		sql += " order by 1 desc";
		
		List<UsuarioDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

	@Override
	public List<UsuarioResumenDto> listarResumenPorCriterios(SolicitudBusquedaUsuarios criterios) {
		String sql = SQL_SELECT_USUARIO_RESUMEN + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<UsuarioResumenDto> vRowMapper = new BeanPropertyRowMapper<UsuarioResumenDto>(UsuarioResumenDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getIdTipoUsuario() != null && (long) criterios.getIdTipoUsuario() > 0) {
			sql += " and id_tipo_usuario=:id_tipo_usuario ";
			parameters.addValue("id_tipo_usuario", criterios.getIdTipoUsuario());
		}
		
		if (criterios.getUsername() != null && !criterios.getUsername().isEmpty()) {
			sql += " and username=:username ";
			parameters.addValue("username", criterios.getUsername());
		}

		sql += " order by 1 desc";
		
		List<UsuarioResumenDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}


}
