package br.com.fiap.prontuarioback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tb_paciente", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cpf_paciente", columnNames = "cpf_paciente")
})
public class Paciente {
    @Id
    @GeneratedValue(generator = "seq_paciente", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_paciente", sequenceName = "seq_paciente", allocationSize = 1)
    @Column(name = "id_paciente")
    private Long id;

    @NotNull(message = "nome não pode ser nulo")
    @Column(name = "nome_paciente")
    private String nome;

    @NotNull(message = "cpf não pode ser nulo")
    @Column(name = "cpf_paciente")
    private String cpf;

    @Email
    @Column(name = "email_paciente")
    private String email;

    @NotNull
    @Column(name = "telefone_paciente")
    private String telefone;

}