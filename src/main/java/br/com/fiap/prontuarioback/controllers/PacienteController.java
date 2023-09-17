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
import br.com.fiap.prontuarioback.model.Paciente;
import br.com.fiap.prontuarioback.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
// @SecurityRequirement(name = "bearer-key")
@Tag(name="paciente")
public class PacienteController {
    
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> index(){
        return pacienteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Paciente> create(@RequestBody @Valid Paciente paciente){
        log.info("cadastrando paciente" + paciente);
        pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }

    @GetMapping("{id}")
    public ResponseEntity<Paciente> show(@PathVariable Long id){
        log.info("detalhando paciente" + id);
        return ResponseEntity.ok(getPaciente(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Paciente> destroy(@PathVariable Long id){
        log.info("apagando paciente" + id);

        pacienteRepository.delete(getPaciente(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Paciente> update(@PathVariable Long id, @RequestBody @Valid Paciente paciente){
        log.info("alterando paciente" + id);
        getPaciente(id);
        paciente.setId(id);
        pacienteRepository.save(paciente);
        return ResponseEntity.ok(paciente);
    }

    private Paciente getPaciente(Long id){
        return pacienteRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Paciente não encontrada!"));
    }

}
