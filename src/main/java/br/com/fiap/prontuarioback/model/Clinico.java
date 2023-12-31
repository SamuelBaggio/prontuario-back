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
@Table(name = "tb_clinico", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cpf_clinico", columnNames = "cpf_clinico")
})
public class Clinico {

    @Id
    @GeneratedValue(generator = "seq_clinico", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_clinico", sequenceName = "seq_clinico", allocationSize = 1)
    @Column(name = "id_clinico")
    private Long id;

    @NotNull(message = "nome não pode ser nulo")
    @Column(name = "nome_clinico")
    private String nome;

    @NotNull(message = "cpf não pode ser nulo")
    @Column(name = "cpf_clinico")
    private String cpf;

    @Email
    @Column(name = "email_clinico")
    private String email;

}
