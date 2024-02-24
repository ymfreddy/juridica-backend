package com.mfb.adm.core.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntCita;

public interface ICitaRepository extends JpaRepository<EntCita, UUID> {
	
	List<EntCita> findAllByIdEmpresa(@Param("idEmpresa") Long idEmpresa);

}
