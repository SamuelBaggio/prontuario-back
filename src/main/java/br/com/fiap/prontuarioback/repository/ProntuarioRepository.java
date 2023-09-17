package br.com.fiap.prontuarioback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.prontuarioback.model.Prontuario;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

}
