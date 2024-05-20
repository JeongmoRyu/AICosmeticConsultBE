package ai.maum.mcl.skins.conf.security;

import ai.maum.mcl.skins.meta.RegexMeta;
import ai.maum.mcl.skins.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private String jwtKey;

    public JwtAuthenticationFilter(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        log.debug("jwtKey" + jwtKey);

        if(StringUtil.matches(requestURI, RegexMeta.SERVICEAPI_PATH)) {
            String jwtToken = extractJwtFromRequest(request);

            Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
            if(jwtToken != null && !jwtToken.isEmpty()) {
                try {
                    Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
                    Claims claims = claimsJws.getBody();

                    String username = claims.getSubject();
                    List<SimpleGrantedAuthority> authorityList = ((List<?>) claims.get("rol")).stream()
                            .map(authority -> new SimpleGrantedAuthority ((String) authority))
                            .toList();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorityList);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (Exception e) {
                    log.error("jwtToken is invalid:" + jwtToken + ":" + e.getMessage());
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            } else {
                log.error("jwtToken is invalid:" + jwtToken + ":token is null");
                SecurityContextHolder.clearContext();
            }
        } else if (StringUtil.matches(requestURI, RegexMeta.PUBLICAPI_PATH)
                || StringUtil.matches(requestURI, RegexMeta.EXTAPI_PATH)
                || StringUtil.matches(requestURI, RegexMeta.SWAGGER_PATHS)
            ) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("authorize fail");
            return;
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
