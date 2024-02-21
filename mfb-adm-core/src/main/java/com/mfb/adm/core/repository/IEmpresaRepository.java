package com.mfb.adm.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntEmpresa;

public interface IEmpresaRepository extends JpaRepository<EntEmpresa, Long> {
	Optional<EntEmpresa> findByNit(@Param("nit") long nit);
	Optional<EntEmpresa> findBySigla(@Param("sigla") String sigla);

}
