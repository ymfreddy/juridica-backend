package com.mfb.adm.core.query.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.dtos.UsuarioLogin;
import com.mfb.adm.core.query.ILoginQuery;

@Repository
public class LoginQueryImpl implements ILoginQuery {

	final static String SQL_SELECT_USUARIO = "select us.id, us.id_empresa, us.codigo_tipo_usuario , us.username, \"password\", us.enabled, em.nit  "
			+ "from adm.usuarios us inner join adm.empresas em on us.id_empresa=em.id where em.active is true and us.active is true ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public UsuarioLogin login(String username) {
		String sql = SQL_SELECT_USUARIO + " and us.username =:username ";
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);
		List<UsuarioLogin> dto = jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(UsuarioLogin.class));

		if (!dto.isEmpty())	return dto.get(0);

		String sql2 = SQL_SELECT_USUARIO + " and us.email =:email ";
		SqlParameterSource parameters2 = new MapSqlParameterSource().addValue("email", username);
		List<UsuarioLogin> dto2 = jdbcTemplate.query(sql2, parameters2,
				new BeanPropertyRowMapper<>(UsuarioLogin.class));

		return dto2.isEmpty() ? null : dto2.get(0);
	}

}
