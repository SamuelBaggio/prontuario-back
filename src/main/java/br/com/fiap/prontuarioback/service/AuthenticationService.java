package br.com.fiap.prontuarioback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.prontuarioback.repository.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return usuarioRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username não encontrado"));
    }

}

