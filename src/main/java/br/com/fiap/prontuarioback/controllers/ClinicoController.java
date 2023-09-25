package br.com.fiap.prontuarioback.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.prontuarioback.exceptions.RestNotFoundException;
import br.com.fiap.prontuarioback.model.Clinico;
import br.com.fiap.prontuarioback.repository.ClinicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/clinicos")
public class ClinicoController {
    
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ClinicoRepository clinicoRepository;

    @GetMapping
    public List<Clinico> index(){
        return clinicoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Clinico> create(@RequestBody @Valid Clinico clinico){
        log.info("cadastrando clinico" + clinico);
        clinicoRepository.save(clinico);
        return ResponseEntity.status(HttpStatus.CREATED).body(clinico);
    }

    @GetMapping("{id}")
    public ResponseEntity<Clinico> show(@PathVariable Long id){
        log.info("detalhando clinico" + id);
        return ResponseEntity.ok(getClinico(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Clinico> destroy(@PathVariable Long id){
        log.info("apagando clinico" + id);

        clinicoRepository.delete(getClinico(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Clinico> update(@PathVariable Long id, @RequestBody @Valid Clinico clinico){
        log.info("alterando empresa" + id);
        getClinico(id);
        clinico.setId(id);
        clinicoRepository.save(clinico);
        return ResponseEntity.ok(clinico);
    }

    private Clinico getClinico(Long id){
        return clinicoRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Clinico não encontrada!"));
    }

}