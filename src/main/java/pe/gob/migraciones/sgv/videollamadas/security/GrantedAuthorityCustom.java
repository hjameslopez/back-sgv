package pe.gob.migraciones.sgv.videollamadas.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityCustom implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private String roleName;
    public GrantedAuthorityCustom(String roleName) {
        this.roleName = roleName;
    }
    @Override
    public String getAuthority() {
        return roleName;
    }
}