package com.operalatam.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.operalatam.api.model.Aeronave;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {
    Optional<Aeronave> findByNome(String nome);

    Page<Aeronave> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}