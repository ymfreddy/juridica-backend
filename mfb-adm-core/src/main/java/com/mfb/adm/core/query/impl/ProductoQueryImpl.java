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

import com.mfb.adm.comm.dtos.ProductoDto;
import com.mfb.adm.comm.dtos.ProductoResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaProductos;
import com.mfb.adm.comm.requests.SolicitudListaPaginadaProductos;
import com.mfb.adm.comm.utils.Funciones;
import com.mfb.adm.core.query.IProductoQuery;

@Repository
public class ProductoQueryImpl implements IProductoQuery {

	private final String SQL_SELECT_PRODUCTO = "select * FROM spv.v_productos ";
	private final String SQL_SELECT_PRODUCTO_RESUMEN = "select id, codigo_tipo_producto, codigo_producto, nombre, precio, imagen_nombre FROM spv.v_productos ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public ProductoDto verPorId(Long idProducto) {
		String sql = SQL_SELECT_PRODUCTO + " where id=:id ";
		RowMapper<ProductoDto> vRowMapper = new BeanPropertyRowMapper<ProductoDto>(ProductoDto.class);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", idProducto);
		List<ProductoDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override
	public List<ProductoDto> listarPorCriterios(SolicitudBusquedaProductos criterios) {
		String sql = SQL_SELECT_PRODUCTO + " where ";
		RowMapper<ProductoDto> vRowMapper = new BeanPropertyRowMapper<ProductoDto>(ProductoDto.class);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}

		if (criterios.getIdProducto() != null && (long) criterios.getIdProducto() > 0) {
			sql += " and id=:id_producto ";
			parameters.addValue("id_producto", criterios.getIdProducto());
		}
		
		if (criterios.getCodigosTipoProducto() != null && !criterios.getCodigosTipoProducto().isEmpty()) {
			sql += " and codigo_tipo_producto in (:codigo_tipo_producto) ";
			List<String> listaIds = Arrays.asList(criterios.getCodigosTipoProducto().split(",")).stream().map(s -> s).collect(Collectors.toList());
			parameters.addValue("codigo_tipo_producto", listaIds);
		}

		if (criterios.getCodigoProducto() != null && !criterios.getCodigoProducto().isEmpty()) {
			sql += " and codigo_producto=:codigo_producto ";
			parameters.addValue("codigo_producto", criterios.getCodigoProducto());
		}

		if (criterios.getIdCategoria() != null && (long) criterios.getIdCategoria() > 0) {
			sql += " and id_categoria=:id_categoria ";
			parameters.addValue("id_categoria", criterios.getIdCategoria());
		}
		
		if (criterios.getIdsCategorias() != null && !criterios.getIdsCategorias().isEmpty()) {
			sql += " and id_categoria in (:id_categoria) ";
			List<Long> listaIds = Arrays.asList(criterios.getIdsCategorias().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("id_categoria", listaIds);			
		}
		
		sql += " order by 1 desc";
		
		List<ProductoDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

	@Override
	public List<ProductoResumenDto> listarResumenPorCriterios(SolicitudBusquedaProductos criterios) {
		String sql = SQL_SELECT_PRODUCTO_RESUMEN + " where ";
		RowMapper<ProductoResumenDto> vRowMapper = new BeanPropertyRowMapper<ProductoResumenDto>(
				ProductoResumenDto.class);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		if (criterios.getIdEmpresa() != null && (long) criterios.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", criterios.getIdEmpresa());
		}
		
		if (criterios.getIdProducto() != null && (long) criterios.getIdProducto() > 0) {
			sql += " and id=:id_producto ";
			parameters.addValue("id_producto", criterios.getIdProducto());
		}
		
		if (criterios.getCodigosTipoProducto() != null && !criterios.getCodigosTipoProducto().isEmpty()) {
			sql += " and codigo_tipo_producto in (:codigo_tipo_producto) ";
			List<String> listaIds = Arrays.asList(criterios.getCodigosTipoProducto().split(",")).stream().map(s -> s).collect(Collectors.toList());
			parameters.addValue("codigo_tipo_producto", listaIds);
		}

		if (criterios.getCodigoProducto() != null && !criterios.getCodigoProducto().isEmpty()) {
			sql += " and codigo_producto=:codigo_producto ";
			parameters.addValue("codigo_producto", criterios.getCodigoProducto());
		}

		if (criterios.getIdCategoria() != null && (long) criterios.getIdCategoria() > 0) {
			sql += " and id_categoria=:id_categoria ";
			parameters.addValue("id_categoria", criterios.getIdCategoria());
		}
		
		if (criterios.getIdsCategorias() != null && !criterios.getIdsCategorias().isEmpty()) {
			sql += " and id_categoria in (:id_categoria) ";
			List<Long> listaIds = Arrays.asList(criterios.getIdsCategorias().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("id_categoria", listaIds);			
		}
		
		if (criterios.getTermino() != null && !criterios.getTermino().isEmpty()) {
			sql += " and (nombre ilike :termino or codigo_producto ilike :termino) ";
			String terminoFinal= "%" + criterios.getTermino().trim() + "%";
			parameters.addValue("termino", terminoFinal);
		}
		
		sql += " order by codigo_producto ";
		
		if (criterios.getCantidadRegistro() != null && criterios.getCantidadRegistro()>0) {
			sql += " LIMIT :cantidadRegistro ";
			parameters.addValue("cantidadRegistro", criterios.getCantidadRegistro());
		}
		List<ProductoResumenDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		return lista;
	}

	@Override
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginadaProductos solicitud) {
		String sql = SQL_SELECT_PRODUCTO + " where ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		RowMapper<ProductoDto> vRowMapper = new BeanPropertyRowMapper<ProductoDto>(ProductoDto.class);
		if (solicitud.getIdEmpresa() != null && (long) solicitud.getIdEmpresa() > 0) {
			sql += " id_empresa=:id_empresa ";
			parameters.addValue("id_empresa", solicitud.getIdEmpresa());
		}
		if (solicitud.getIdProducto() != null && (long) solicitud.getIdProducto() > 0) {
			sql += " and id_producto=:id_producto ";
			parameters.addValue("id_producto", solicitud.getIdProducto());
		}
		if (solicitud.getCodigosTipoProducto() != null && !solicitud.getCodigosTipoProducto().isEmpty()) {
			sql += " and codigo_tipo_producto in (:codigo_tipo_producto) ";
			List<String> listaIds = Arrays.asList(solicitud.getCodigosTipoProducto().split(",")).stream().map(s -> s).collect(Collectors.toList());
			parameters.addValue("codigo_tipo_producto", listaIds);
		}
		if (solicitud.getIdsCategorias() != null && !solicitud.getIdsCategorias().isEmpty()){
			sql += " and id_categoria in (:campo)";
			List<Long> listaIds = Arrays.asList(solicitud.getIdsCategorias().split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			parameters.addValue("campo", listaIds);
		}
		if (solicitud.getFiltro() != null && !solicitud.getFiltro().isEmpty()) {
			sql += " and (codigo_producto ilike :terminoFinal or nombre ilike :terminoFinal or categoria ilike :terminoFinal )";
			String terminoFinal = "%" + solicitud.getFiltro().trim() + "%";
			parameters.addValue("terminoFinal", terminoFinal);
		}		
		RespuestaListaPaginada respuesta = new RespuestaListaPaginada();
		Object total = jdbcTemplate.queryForObject(sql.replace("*", "COUNT(0)"), parameters, Object.class);
		
		if (solicitud.getCampoOrden() == null || solicitud.getCampoOrden().isEmpty()) {
			sql += " order by 1 desc";
		}
		else {
			sql += " order by "+ Funciones.camelToSnake(solicitud.getCampoOrden())  + (solicitud.getTipoOrden()==-1 ? " desc ":" asc ");
		}
		
		respuesta.setTotalItems(Integer.parseInt(total.toString()));
		respuesta.setPaginaActual(solicitud.getPagina());
		respuesta.setPaginasTotales(respuesta.getTotalItems() / solicitud.getCantidadItems());
		Integer ofset = (solicitud.getPagina() - 1) * solicitud.getCantidadItems();
		sql += " LIMIT :cantidad_registros OFFSET :ofset ";
		parameters.addValue("cantidad_registros", solicitud.getCantidadItems());
		parameters.addValue("ofset", ofset);
		List<ProductoDto> lista = this.jdbcTemplate.query(sql, parameters, vRowMapper);
		respuesta.setItems(lista);
		return respuesta;
	}
}
