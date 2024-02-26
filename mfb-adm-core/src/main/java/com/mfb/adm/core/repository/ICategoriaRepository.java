package com.mfb.adm.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntCategoria;

public interface ICategoriaRepository extends JpaRepository<EntCategoria, Long> {
	List<EntCategoria> findAllByIdEmpresa(@Param("idEmpresa") long idEmpresa);

	Optional<EntCategoria> findByNombreAndIdEmpresa(@Param("nombre") String nombre, @Param("idEmpresa") long idEmpresa);
}
