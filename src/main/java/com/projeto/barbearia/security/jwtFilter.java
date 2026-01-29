package com.projeto.barbearia.security;

import com.projeto.barbearia.service.ClienteService;
import com.projeto.barbearia.service.JwtService;
import com.projeto.barbearia.service.UsuariosService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class jwtFilter extends OncePerRequestFilter {

    private final JwtService tokenService;
    private final UsuariosService usuariosService;



    @Autowired
    public jwtFilter(JwtService tokenService,  UsuariosService usuariosService) {
        this.tokenService = tokenService;
        this.usuariosService = usuariosService;
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return Optional.empty();
        }

        return Optional.of(
                authHeader.replace("Bearer ", "")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, ServletException {

        String path = request.getRequestURI();
        if (path.equals("/persons/admins") || path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }


        Optional<String> token = extractToken(request);

        if (token.isPresent()) {
            String subject = tokenService.validateToken(token.get());

            UserDetails userDetails = usuariosService.loadUserByUsername(subject);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        filterChain.doFilter(request, response);
    }

    @Component
    public static class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

        @Autowired
        private JwtService tokenService;

        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication) throws IOException {

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String email = oAuth2User.getAttribute("email");

            String token = tokenService.generateToken(email, "ROLE_CLIENTE" );

            response.setContentType("application/json");
            response.getWriter().write("""
            {
              "token": "%s"
            }
        """.formatted(token));
        }
    }

}
