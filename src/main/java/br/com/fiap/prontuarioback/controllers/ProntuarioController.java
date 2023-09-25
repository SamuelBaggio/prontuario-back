package br.com.fiap.prontuarioback.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
import br.com.fiap.prontuarioback.model.Prontuario;
import br.com.fiap.prontuarioback.repository.ClinicoRepository;
import br.com.fiap.prontuarioback.repository.PacienteRepository;
import br.com.fiap.prontuarioback.repository.ProntuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/prontuario")
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class ProntuarioController {
    
    @Autowired
    ProntuarioRepository prontuarioRepository;

    @Autowired
    ClinicoRepository clinicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @GetMapping
    public List<Prontuario> index(){

            return prontuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Prontuario prontuario) {
        log.info("cadastrando prontuario" + prontuario);
        prontuarioRepository.save(prontuario);
        prontuario.setClinico(clinicoRepository.findById(prontuario.getClinico().getId()).get());        
        // prontuario.setPaciente(pacienteRepository.findById(prontuario.getPaciente().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuario.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Prontuario> show(@PathVariable Long id){
        log.info("detalhando prontuario" + id);

        var prontuario = prontuarioRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("prontuario não encontrado!"));

        return prontuario.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Prontuario> destroy(@PathVariable Long id){
        log.info("apagando prontuario" + id);
        var prontuario = prontuarioRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("prontuario não encontrado!"));

        prontuarioRepository.delete(prontuario);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Prontuario> update(@PathVariable Long id, @RequestBody @Valid Prontuario prontuario){
        log.info("alterando prontuario" + id);

        prontuarioRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("prontuario não encontrado!"));

        prontuario.setId(id);
        prontuarioRepository.save(prontuario);

        return prontuario.toEntityModel();
    }
}
