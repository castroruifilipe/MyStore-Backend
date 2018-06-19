package mystore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import mystore.models.enums.RoleUtilizador;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Authorization header inválido");
        }

        final String token = authHeader.replace("Bearer ", "");

        try {
            final Claims claims = Jwts.parser().setSigningKey("SECRET")
                    .parseClaimsJws(token).getBody();
            request.setAttribute("uid", claims.getSubject());
            request.setAttribute("role", claims.get("role", RoleUtilizador.class));
        } catch (final SignatureException e) {
            throw new ServletException("Token inválido");
        }

        filterChain.doFilter(req, res);
    }

}
