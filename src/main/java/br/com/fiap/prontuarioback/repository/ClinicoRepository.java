package br.com.fiap.prontuarioback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.prontuarioback.model.Clinico;

public interface ClinicoRepository extends JpaRepository<Clinico, Long>{
    
}
