package com.mfb.adm.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfb.adm.core.entity.EntRecurso;

public interface IRecursoRepository extends JpaRepository<EntRecurso, Long> {}