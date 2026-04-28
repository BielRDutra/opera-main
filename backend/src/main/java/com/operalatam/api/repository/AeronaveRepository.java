package com.operalatam.api.repository;

import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.operalatam.api.model.Aeronave;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {
    Optional<Aeronave> findByNome(String nome);

    Page<Aeronave> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Slice<Aeronave> findByNomeContainingIgnoreCase(String nomeFilter, PageRequest of);
}