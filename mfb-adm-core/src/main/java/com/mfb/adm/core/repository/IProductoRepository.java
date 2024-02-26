package com.mfb.adm.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntProducto;

public interface IProductoRepository extends JpaRepository<EntProducto, Long> {
	List<EntProducto> findAllByIdEmpresa(@Param("idEmpresa") long idEmpresa);

	Optional<EntProducto> findByCodigoProductoAndIdEmpresa(@Param("codigoProducto") String codigoProducto,
			@Param("idEmpresa") long idEmpresa);

}
