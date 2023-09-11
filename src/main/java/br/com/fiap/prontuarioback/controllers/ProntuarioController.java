package br.com.fiap.prontuarioback.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.prontuarioback.exceptions.RestNotFoundException;
import br.com.fiap.prontuarioback.model.Prontuario;
import br.com.fiap.prontuarioback.repository.ClinicoRepository;
import br.com.fiap.prontuarioback.repository.PacienteRepository;
import br.com.fiap.prontuarioback.repository.ProntuarioRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/prontuario")
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name="prontuario")
public class ProntuarioController {
    
    @Autowired
    ProntuarioRepository prontuarioRepository;

    @Autowired
    ClinicoRepository clinicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca,
        @ParameterObject @PageableDefault(size=3) Pageable pageable){

            Page<Prontuario> prontuarios = (busca == null) ?
                prontuarioRepository.findAll(pageable):
                prontuarioRepository.findByNomeContaining(busca, pageable);

            return assembler.toModel(prontuarios.map(Prontuario::toEntityModel));
    }

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Prontuario criado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Os campos são inválidos")
    })
    public ResponseEntity<Object> create(@RequestBody @Valid Prontuario prontuario) {
        log.info("cadastrando prontuario" + prontuario);
        prontuarioRepository.save(prontuario);
        prontuario.setClinico(clinicoRepository.findById(prontuario.getClinico().getId()).get());        
        prontuario.setPaciente(pacienteRepository.findById(prontuario.getPaciente().getId()).get());
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
