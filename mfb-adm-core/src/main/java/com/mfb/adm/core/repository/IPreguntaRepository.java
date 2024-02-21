package com.mfb.adm.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfb.adm.core.entity.EntPregunta;

public interface IPreguntaRepository extends JpaRepository<EntPregunta, Long> {}
