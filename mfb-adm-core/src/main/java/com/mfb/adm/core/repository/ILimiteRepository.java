package com.mfb.adm.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntLimite;

public interface ILimiteRepository extends JpaRepository<EntLimite, Long> {
	Optional<EntLimite> findByUsernameAndDia(@Param("username") String username, @Param("dia") String dia);
}
