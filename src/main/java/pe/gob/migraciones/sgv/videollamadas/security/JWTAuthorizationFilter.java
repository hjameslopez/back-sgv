package pe.gob.migraciones.sgv.videollamadas.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private String sHeaderAuthorizationKey;
    private String sTokenBearerPreix;
    private final TokenJwtClientCustom tokenJwtClientCustom;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String headerAuthorizationKey, String tokenBearerPreix, /*String jwtSecretKey, */TokenJwtClientCustom tokenJwtClientCustom) {
        super(authManager);
        sHeaderAuthorizationKey = headerAuthorizationKey;
        sTokenBearerPreix = tokenBearerPreix;
        this.tokenJwtClientCustom = tokenJwtClientCustom;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(sHeaderAuthorizationKey);
        if (header == null || !header.startsWith(sTokenBearerPreix)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(sHeaderAuthorizationKey);
        if (token != null) {
            token = token.replace(sTokenBearerPreix, "");
            UserDetailsCustom user = tokenJwtClientCustom.getUserDetailsFromToken(token, request);
            if (user != null) {
                List<GrantedAuthorityCustom> lista = new ArrayList<>();
                for (Object g : user.getAuthorities()) {
                    Map<String, Object> xy = (HashMap) g;
                    lista.add(new GrantedAuthorityCustom(xy.get("authority").toString()));
                }
                return new UsernamePasswordAuthenticationToken(user, null, lista);
            }
            return null;
        }
        return null;
    }
}
