package br.com.fiap.prontuarioback.model;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.prontuarioback.controllers.ClinicoController;
import br.com.fiap.prontuarioback.controllers.PacienteController;
import br.com.fiap.prontuarioback.controllers.ProntuarioController;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_prontuario")
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "nome não pode ser nulo")
    private String nomePaciente;

    @NotBlank
    @Size(min = 5, max = 255)
    private String sintomas;

    @NotBlank
    @Size(min = 5, max = 255)
    private String diagnostico;

    @NotBlank
    @Size(min = 5, max = 255)
    private String medicamentos;

    // @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
    // @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", foreignKey = @ForeignKey(name = "fk_prontuario_paciente"))
    // private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH })
    @JoinColumn(name = "id_clinico", referencedColumnName = "id_clinico", foreignKey = @ForeignKey(name = "fk_prontuario_clinico"))
    private Clinico clinico;

    public EntityModel<Prontuario> toEntityModel(){
    return EntityModel.of(
            this,
            linkTo(methodOn(ProntuarioController.class).show(id)).withSelfRel(),
            linkTo(methodOn(ProntuarioController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(ProntuarioController.class).index()).withRel("all"),
            // linkTo(methodOn(PacienteController.class).show(this.getPaciente().getId())).withRel("paciente"),           
            linkTo(methodOn(ClinicoController.class).show(this.getClinico().getId())).withRel("clinico")

        );
    }
}