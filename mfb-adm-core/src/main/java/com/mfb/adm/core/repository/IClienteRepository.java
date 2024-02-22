package com.mfb.adm.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntCliente;

public interface IClienteRepository extends JpaRepository<EntCliente, Long> {
	
	List<EntCliente> findAllByIdEmpresa(@Param("idEmpresa") Long idEmpresa);

	Optional<EntCliente> findByCodigoClienteAndIdEmpresa(@Param("codigoCliente") String codigoCliente,
			@Param("idEmpresa") long idEmpresa);
	
	Boolean existsByCodigoClienteAndIdEmpresa(@Param("codigoCliente") String codigoCliente,
			@Param("idEmpresa") long idEmpresa);

}
