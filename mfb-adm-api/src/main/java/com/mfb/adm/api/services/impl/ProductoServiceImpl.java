package com.mfb.adm.api.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mfb.adm.api.services.IProductoService;
import com.mfb.adm.comm.constants.ConstAdm;
import com.mfb.adm.comm.constants.MsgAdm;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.ProductoDto;
import com.mfb.adm.comm.dtos.ProductoResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaProductos;
import com.mfb.adm.comm.requests.SolicitudCargaMasivaProductos;
import com.mfb.adm.comm.requests.SolicitudListaPaginadaProductos;
import com.mfb.adm.core.entity.EntEmpresa;
import com.mfb.adm.core.entity.EntProducto;
import com.mfb.adm.core.query.IProductoQuery;
import com.mfb.adm.core.repository.IEmpresaRepository;
import com.mfb.adm.core.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoRepository repositorio;

	@Autowired
	private IEmpresaRepository repositorioEmpresa;

	@Autowired
	private IProductoQuery query;

	@Override
	//@Cacheable(key = "#id", value = "Productos")
	public RespuestaRest verPorId(Long id) {
		ProductoDto existe = query.verPorId(id);
		if (existe == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		//cargarDatosAdicionales(existe, 0L);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_FIND_SUCCESSFULLY).content(existe).build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Productos", allEntries = true)
	public RespuestaRest registrar(ProductoDto dto) {
		EntEmpresa empresa = repositorioEmpresa.findById(dto.getIdEmpresa()).orElse(null);
		if (empresa == null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESAS_NO_EXISTE).build();
		}
		// validar codigo producto existente
		EntProducto ent = repositorio.findByCodigoProductoAndIdEmpresa(dto.getCodigoProducto(), dto.getIdEmpresa())
				.orElse(null);
		if (ent != null) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_PRODUCTOS_EXISTE_CODIGO + dto.getCodigoProducto())
					.build();
		}
		
		// insertar
		ent = new ModelMapper().map(dto, EntProducto.class);
		ent.setId(null);
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_PERSIST_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Productos", allEntries = true)
	public RespuestaRest actualizar(ProductoDto dto) {
		// validar codigo producto existente
		EntProducto ent = repositorio.findById(dto.getId()).orElse(null);
		if (ent == null) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_NOT_FOUND_RECORD).build();
		}
		// validar codigo producto existente
		EntProducto existe = repositorio.findByCodigoProductoAndIdEmpresa(dto.getCodigoProducto(), dto.getIdEmpresa())
				.orElse(null);
		if (existe != null && !existe.getId().equals(dto.getId())) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_PRODUCTOS_EXISTE_CODIGO + dto.getCodigoProducto())
					.build();
		}
		
		// insertar
		ent.setCodigoProducto(dto.getCodigoProducto());
		ent.setCodigoActividadSin(dto.getCodigoActividadSin());
		ent.setCodigoProductoSin(dto.getCodigoProductoSin());
		ent.setCodigoTipoUnidadSin(dto.getCodigoTipoUnidadSin());
		ent.setCosto(dto.getCosto());
		ent.setPrecio(dto.getPrecio());
		ent.setIdCategoria(dto.getIdCategoria());
		ent.setDescripcion(dto.getDescripcion());
		ent.setCodigoTipoProducto(dto.getCodigoTipoProducto());
		ent.setNombre(dto.getNombre());
		ent.setImagenNombre(dto.getImagenNombre());
		ent = repositorio.save(ent);
		return RespuestaRest.builder().success(true).message(MsgApp.RESPONSE_UPDATE_SUCCESSFULLY).content(ent.getId())
				.build();
	}

	@Override
	@Transactional
	@CacheEvict(value = "Productos", allEntries = true)
	public RespuestaRest eliminar(Long id) {
		// validar codigo producto existente
		EntProducto ent = repositorio.findById(id).orElse(null);
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
	@Transactional
	@CacheEvict(value = "Productos", allEntries = true)
	public RespuestaRest registrarMasivo(SolicitudCargaMasivaProductos solicitud) {
		return null;
	}

	@Override
	@Cacheable(key = "#criterios", value = "Productos")
	public RespuestaRest listarPorCriterios(SolicitudBusquedaProductos criterios) {
		if (criterios.getIdEmpresa() == null || (long) criterios.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESA_ID_NO_ENVIADO).build();
		}

		if (criterios.getResumen()) {
			List<ProductoResumenDto> lista = query.listarResumenPorCriterios(criterios);
			/*for (ProductoResumenDto productoDto : lista) {
				this.cargarResumenDatosAdicionales(productoDto, criterios.getIdSucursal());
			}*/
			return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
					.content(lista).build();
		}

		List<ProductoDto> lista = query.listarPorCriterios(criterios);
		/*for (ProductoDto productoDto : lista) {
			this.cargarDatosAdicionales(productoDto, criterios.getIdSucursal());
		}*/

		/*
		 * if ( criterios.getIdSucursal()!=null && (long)criterios.getIdSucursal() > 0)
		 * { // se elimina los productos que no tienen saldo lista =
		 * lista.stream().filter(x->x.getSaldo().compareTo(ConstApp.VALOR_0)==1 ||
		 * !x.getIdTipoProducto().equals(EnumTipoProducto.
		 * TIPO_PRODUCTO_PRODUCTO_CON_INVENTARIO.getValor())).collect(Collectors.toList(
		 * )); }
		 */

		return RespuestaRest.builder().success(true).message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.size()))
				.content(lista).build();
	}
	
	@Override
	@Cacheable(key = "#paginacion", value = "Productos")
	public RespuestaRest listarPaginado(SolicitudListaPaginadaProductos paginacion) {
		if (paginacion.getIdEmpresa() == null || (long) paginacion.getIdEmpresa() == 0) {
			return RespuestaRest.builder().message(MsgAdm.RESPONSE_EMPRESA_ID_NO_ENVIADO).build();
		}

		if (paginacion.getCantidadItems() <= 0 || paginacion.getPagina() <= 0) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_PAGE_REQUEST_ERROR).build();
		}

		/*
		 * EntUsuario usuario =
		 * repositorioUsuario.findByUsername(username).orElse(null); if (usuario!=null
		 * && usuario.getCategorias()!=null && !usuario.getCategorias().isEmpty()) {
		 * Map<String, String> campo = new HashMap<String, String>();
		 * campo.put("categorias", usuario.getCategorias()); solicitud.setCampo(campo);
		 * }
		 */

		RespuestaListaPaginada lista = query.listarPaginado(paginacion);
		@SuppressWarnings("unchecked")
		List<ProductoDto> listaConvertida = (List<ProductoDto>) lista.getItems();
		/*for (ProductoDto productoDto : listaConvertida) {
			this.cargarDatosAdicionales(productoDto, paginacion.getIdSucursal());
		}*/
		lista.setItems(listaConvertida);

		return RespuestaRest.builder().success(true)
				.message(String.format(MsgApp.RESPONSE_FIND_LIST, lista.getTotalItems())).content(lista).build();
	}

	@Override
	public void cargarResumenDatosAdicionales(List<ProductoResumenDto> lista, Long idSucursal) {
		for (ProductoResumenDto dto : lista) {
			// asignar ruta imagen
			if (dto.getImagenNombre() != null && !dto.getImagenNombre().isEmpty()) {
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(ConstAdm.RUTA_DESCARGA)
						.path(dto.getImagenNombre()).toUriString();
				dto.setImagenRuta(
						fileDownloadUri.contains("localhost") || fileDownloadUri.contains("192.") ? fileDownloadUri
								: fileDownloadUri.replace("http://", "https://"));
			}
		}		
	}

	@Override
	public void cargarDatosAdicionales(List<ProductoDto> lista, Long idSucursal) {
		for (ProductoDto dto : lista) {
			cargarDatosAdicionales(dto, idSucursal);
		}		
	}
	
	@Override
	public void cargarDatosAdicionales(ProductoDto dto, Long idSucursal) {
			// asignar ruta imagen
			if (dto.getImagenNombre() != null && !dto.getImagenNombre().isEmpty()) {
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(ConstAdm.RUTA_DESCARGA).path(dto.getImagenNombre()).toUriString();
				dto.setImagenRuta(fileDownloadUri.contains("localhost") || fileDownloadUri.contains("192.") ? fileDownloadUri	: fileDownloadUri.replace("http://", "https://"));
			}
	}
}
