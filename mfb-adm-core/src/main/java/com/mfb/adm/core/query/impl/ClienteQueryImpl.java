package com.mfb.adm.core.query.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mfb.adm.comm.dtos.ClienteDto;
import com.mfb.adm.comm.dtos.ClienteResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaClientes;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.comm.utils.Funciones;
import com.mfb.adm.core.query.IClienteQuery;

@Repository
public class ClienteQueryImpl implements IClienteQuery {

	private final String SQL_SELECT_CLIENTE = "select * FROM adm.v_clientes ";
	private final String SQL_SELECT_CLIENTE_TELEFONO = "select telefono FROM adm.v_clientes where nit=:nit and codigo_cliente=:codigo_cliente ";
	private final String SQL_SELECT_CLIENTE_RESUMEN = "select id, codigo_cliente, nombre, email, telefono FROM adm.v_clientes ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public ClienteDto verPorId(Long idCliente) {
		String sql = SQL_SELECT_CLIENTE + " where id=:id ";
		RowMapper<ClienteDto> vRowMapper = new BeanPropertyRowMapper<ClienteDto>(ClienteDto.class);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", idCliente);
		List<ClienteDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override
	public String verTelefonoPorIdEmpresaYCodigo(Long idEmpresa, String codigoCliente) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id_empresa", idEmpresa)
				.addValue("codigo_cliente", codigoCliente);
		try {
			String telefono = this.jdbcTemplate.queryForObject(SQL_SELECT_CLIENTE_TELEFONO, parameters, String.class);
			return telefono;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ClienteDto> listarPorCriterios(SolicitudBusquedaClientes criterios) {
		String sql = SQL_SELECT_CLIENTE + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<ClienteDto> vRowMapper = new BeanPropertyRowMapper<ClienteDto>(ClienteDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getCodigoTipoDocumento() != null && !criterios.getCodigoTipoDocumento().isEmpty()) {
			sql += " and codigo_tipo_documento_identidad=:codigo_tipo_documento_identidad ";
			parameters.addValue("codigo_tipo_documento_identidad", criterios.getCodigoTipoDocumento());
		}

		if (criterios.getCodigoCliente() != null && !criterios.getCodigoCliente().isEmpty()) {
			sql += " and codigo_cliente=:codigo_cliente ";
			parameters.addValue("codigo_cliente", criterios.getCodigoCliente());
		}

		sql += " order by 1 desc";

		List<ClienteDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

	@Override
	public List<ClienteResumenDto> listarResumenPorCriterios(SolicitudBusquedaClientes criterios) {
		String sql = SQL_SELECT_CLIENTE_RESUMEN + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<ClienteResumenDto> vRowMapper = new BeanPropertyRowMapper<ClienteResumenDto>(ClienteResumenDto.class);
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		if (criterios.getCodigoTipoDocumento() != null && !criterios.getCodigoTipoDocumento().isEmpty()) {
			sql += " and codigo_tipo_documento_identidad=:codigo_tipo_documento_identidad ";
			parameters.addValue("codigo_tipo_documento_identidad", criterios.getCodigoTipoDocumento());
		}

		if (criterios.getCodigoCliente() != null && !criterios.getCodigoCliente().isEmpty()) {
			sql += " and codigo_cliente=:codigo_cliente ";
			parameters.addValue("codigo_cliente", criterios.getCodigoCliente());
		}

		if (criterios.getTermino() != null && !criterios.getTermino().isEmpty()) {
			sql += " and (codigo_cliente ilike :termino or nombre ilike :termino )";
			String terminoFinal = "%" + criterios.getTermino().trim() + "%";
			parameters.addValue("termino", terminoFinal);
		}

		if (criterios.getCantidadRegistros() != null && criterios.getCantidadRegistros() > 0) {
			sql += " ORDER BY codigo_cliente LIMIT :cantidad_registros ";
			parameters.addValue("cantidad_registros", criterios.getCantidadRegistros());
		}

		List<ClienteResumenDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

	@Override
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginada solicitud) {
		String sql = SQL_SELECT_CLIENTE + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<ClienteDto> vRowMapper = new BeanPropertyRowMapper<ClienteDto>(ClienteDto.class);
		if (solicitud.getIdEmpresa() != null && (long) solicitud.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", solicitud.getIdEmpresa());
		}
		if (solicitud.getFiltro() != null || !solicitud.getFiltro().isEmpty()) {
			sql += " and (codigo_cliente ilike :terminoFinal or nombre ilike :terminoFinal )";
			String terminoFinal = "%" + solicitud.getFiltro().trim() + "%";
			parameters.addValue("terminoFinal", terminoFinal);
		}

		RespuestaListaPaginada respuesta = new RespuestaListaPaginada();
		Object total = jdbcTemplate.queryForObject(sql.replace("*", "COUNT(0)"), parameters, Object.class);

		if (solicitud.getCampoOrden() == null || solicitud.getCampoOrden().isEmpty()) {
			sql += " order by 1 desc";
		} else {
			sql += " order by " + Funciones.camelToSnake(solicitud.getCampoOrden())
					+ (solicitud.getTipoOrden() == -1 ? " desc " : " asc ");
		}

		respuesta.setTotalItems(Integer.parseInt(total.toString()));
		respuesta.setPaginaActual(solicitud.getPagina());
		respuesta.setPaginasTotales(respuesta.getTotalItems() / solicitud.getCantidadItems());
		Integer ofset = (solicitud.getPagina() - 1) * solicitud.getCantidadItems();
		sql += " LIMIT :cantidad_registros OFFSET :ofset ";
		parameters.addValue("cantidad_registros", solicitud.getCantidadItems());
		parameters.addValue("ofset", ofset);
		List<ClienteDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		respuesta.setItems(lista);
		return respuesta;
	}

}
