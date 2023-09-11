package br.com.fiap.prontuarioback.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.prontuarioback.model.Usuario;
import br.com.fiap.prontuarioback.service.TokenJwtService;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    TokenJwtService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        var token = getTokenFromHeader(request);

        // if(token != null){
        //     var usuario = service.validate(token);
        //     Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        //     SecurityContextHolder.getContext().setAuthentication(auth);
        // }

        if(token != null){
            Usuario usuario = service.validate(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        var header = request.getHeader("Authorization");

        if(header ==  null || !header.startsWith("Bearer ") || header.isEmpty()){
            return null;
        }

        return header.replace("Bearer ", "");

    }


}
