package com.mfb.adm.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntCorrelativo;

public interface ICorrelativoRepository extends JpaRepository<EntCorrelativo, Long> {
	Optional<EntCorrelativo> findByIdEmpresaAndGestionAndOperacion(@Param("idEmpresa") Long idEmpresa,
			@Param("gestion") Short gestion, @Param("operacion") String operacion);
	
	Boolean existsByIdEmpresa(@Param("idEmpresa") Long idEmpresa);
}
