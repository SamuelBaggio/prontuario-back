package br.com.fiap.prontuarioback.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.prontuarioback.model.Clinico;
import br.com.fiap.prontuarioback.model.Paciente;
import br.com.fiap.prontuarioback.model.Prontuario;
import br.com.fiap.prontuarioback.repository.ClinicoRepository;
import br.com.fiap.prontuarioback.repository.PacienteRepository;
import br.com.fiap.prontuarioback.repository.ProntuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{
    
    @Autowired
    ProntuarioRepository prontuarioRepository;

    @Autowired
    ClinicoRepository clinicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public void run(String... args) throws Exception {

        Clinico c1 = new Clinico(1l, "João Carlos", "11111111123", "joao.carlos@gmail.com");
        Clinico c2 = new Clinico(2l, "Roberto Carlos", "11111111124", "roberto.carlos@gmail.com");
        clinicoRepository.saveAll(List.of(c1, c2));

        Paciente p1 = new Paciente(1l, "Samuel Santos", "37062667846", "samuelsantos@gmail.com", "1133433323");        
        Paciente p2 = new Paciente(2l, "Letícia Santos", "37062667849", "leticiasantos@gmail.com", "11333463323");
        pacienteRepository.saveAll(List.of(p1, p2));

        prontuarioRepository.saveAll(List.of(
            Prontuario.builder().nomePaciente("Samuel").sintomas("Tosse").diagnostico("Alergia").medicamentos("Predinisolona").paciente(p1).clinico(c2).build(),            
            Prontuario.builder().nomePaciente("Letícia").sintomas("Tontura").diagnostico("Enxaqueca").medicamentos("Dipirona").paciente(p2).clinico(c1).build()
        ));

    }

    

}
