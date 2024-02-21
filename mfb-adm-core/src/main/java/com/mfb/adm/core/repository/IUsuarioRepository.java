package com.mfb.adm.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mfb.adm.core.entity.EntUsuario;

public interface IUsuarioRepository extends JpaRepository<EntUsuario, Long> {
	List<EntUsuario> findAllByIdEmpresa(@Param("idEmpresa") long idEmpresa);
	Optional<EntUsuario> findByUsername(@Param("username") String username);
	Optional<EntUsuario> findByEmail(@Param("email") String email);
}
