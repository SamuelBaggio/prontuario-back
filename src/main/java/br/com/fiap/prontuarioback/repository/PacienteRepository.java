package br.com.fiap.prontuarioback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.prontuarioback.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
}
