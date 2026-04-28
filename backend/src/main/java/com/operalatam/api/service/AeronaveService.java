package com.operalatam.api.service;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.operalatam.api.model.Aeronave;
import com.operalatam.api.repository.AeronaveRepository;

@Service
public class AeronaveService {

    private final AeronaveRepository aeronaveRepository;

    public AeronaveService(AeronaveRepository aeronaveRepository) {
        this.aeronaveRepository = aeronaveRepository;
    }

    public Page<Aeronave> listAeronaves(String nomeFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (nomeFilter == null || nomeFilter.isBlank()) {
            return aeronaveRepository.findAll(pageable);
        }
        return aeronaveRepository.findByNomeContainingIgnoreCase(nomeFilter, (org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable) pageable);
    }

    public String getAeronave(String nome) {
        return aeronaveRepository.findByNome(nome).map(Aeronave::getNome).orElse(null);
    }

    public boolean existsByNome(String nome) {
        return aeronaveRepository.findByNome(nome).isPresent();
    }

    public Aeronave createAeronaveEntity(Aeronave aeronave) {
        return aeronaveRepository.save(aeronave);
    }

    public String deleteAeronave(Long id) {
        aeronaveRepository.deleteById(id);
        return "Aeronave deletada";
    }

    public Aeronave createAeronave(String nome) {
        Aeronave a = new Aeronave();
        a.setNome(nome);
        return aeronaveRepository.save(a);
    }

    public List<Aeronave> listAll() {
        return aeronaveRepository.findAll();
    }

    public List<Aeronave> listAllFiltered(String nomeFilter) {
        if (nomeFilter == null || nomeFilter.isBlank()) {
            return aeronaveRepository.findAll();
        }
        // reuse repository method with large pageable to get all matching
        return aeronaveRepository.findByNomeContainingIgnoreCase(nomeFilter, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

}