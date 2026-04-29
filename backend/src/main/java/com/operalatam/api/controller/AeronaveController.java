package com.operalatam.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operalatam.api.dto.AeronaveResponseDTO;
import com.operalatam.api.dto.AeronaveResultDTO;
import com.operalatam.api.dto.InputCreateSetorDTO;
import com.operalatam.api.dto.ListAeronavesRequestDTO;
import com.operalatam.api.model.Aeronave;
import com.operalatam.api.service.AeronaveService;


@RestController
@RequestMapping("/aeronaves")
public class AeronaveController {

    private final AeronaveService aeronaveService;

    public AeronaveController(AeronaveService aeronaveService) {
        this.aeronaveService = aeronaveService;
    }

    @PostMapping
    public ResponseEntity<?> createAeronave(@RequestBody InputCreateSetorDTO setorDto) {
        String nome = setorDto.getNome();
        if (nome == null || nome.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new AeronaveResultDTO(false, "Nome deve ser informado"));
        }

        if (aeronaveService.existsByNome(nome)) {
            return ResponseEntity.status(409)
                    .body(new AeronaveResultDTO(false, "Prefixo já existente"));
        }

        try {
            aeronaveService.createAeronave(nome);
            return ResponseEntity.ok(new AeronaveResultDTO(true, "Aeronave criada com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body(new AeronaveResultDTO(false, "Erro ao salvar aeronave: " + ex.getMessage()));
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listSetores(@ModelAttribute ListAeronavesRequestDTO req) {
        Integer page = req.getPage();
        Integer size = req.getSize();
        String nome = req.getNome();

        if (page == null && size == null) {
            // return full list (nome is optional)
            List<Aeronave> all = aeronaveService.listAllFiltered(nome);
            List<AeronaveResponseDTO> dto = all.stream()
                    .map(s -> new AeronaveResponseDTO(s.getId(), s.getNome()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dto);
        }

        int p = page == null ? 0 : page;
        int s = size == null ? 10 : size;
        Page<Aeronave> pg = aeronaveService.listAeronaves(nome, p, s);
        Page<AeronaveResponseDTO> dtoPage = pg.map(sx -> new AeronaveResponseDTO(sx.getId(), sx.getNome()));
        return ResponseEntity.ok(dtoPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAeronave(@PathVariable Long id) {
        try {
            aeronaveService.deleteAeronave(id);
            return ResponseEntity.ok(new AeronaveResultDTO(true, "Aeronave deletada com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body(new AeronaveResultDTO(false, "Erro ao deletar aeronave: " + ex.getMessage()));
        }
    }
}

