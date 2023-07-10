package pe.gob.migraciones.sgv.videollamadas.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class SecurityConstants {
    @Value("${auth.headerAuthorizationKey}")
    private String headerAuthorizationKey;
    @Value("${auth.tokenBearerPreix}")
    private String tokenBearerPreix;
    public SecurityConstants() {
    }

    public SecurityConstants(String headerAuthorizationKey, String tokenBearerPreix) {
        this.headerAuthorizationKey = headerAuthorizationKey;
        this.tokenBearerPreix = tokenBearerPreix;
    }

    public String getHeaderAuthorizationKey() {
        return headerAuthorizationKey;
    }

    public void setHeaderAuthorizationKey(String headerAuthorizationKey) {
        this.headerAuthorizationKey = headerAuthorizationKey;
    }

    public String getTokenBearerPreix() {
        return tokenBearerPreix;
    }

    public void setTokenBearerPreix(String tokenBearerPreix) {
        this.tokenBearerPreix = tokenBearerPreix;
    }
}

