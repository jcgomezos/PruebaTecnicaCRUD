package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader= request.getHeader("Authorization");
        /*if(token ==null || token == ""){
            throw new RuntimeException("El token enviado no es valido");
        }*/
        if(authHeader != null){
            System.out.println("Validamos que el token no es nulo");
            var token = authHeader.replace("Bearer", "");
            //System.out.println(token);
            var nombreUsuario = tokenService.getSubject("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYW1pbG8uZ29tZXoiLCJpc3MiOiJ2b2xsIG1lZCIsImlkIjoxLCJleHAiOjE3MDAwNTkyMzh9.Wtg5l08SfheFUOYATTy_gJ1WnIN4tafWpmog8V_pOXc");
            if(nombreUsuario != null){
                //el token es valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());//forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);


    }
}
