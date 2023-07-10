package pe.gob.migraciones.sgv.videollamadas.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsCustom implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private ControlMigratorioVo controlMigratorioVo;
    private SesionVo sesionVo;
    private Collection<GrantedAuthorityCustom> authorities;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ControlMigratorioVo getControlMigratorioVo() {
        return controlMigratorioVo;
    }

    public void setControlMigratorioVo(ControlMigratorioVo controlMigratorioVo) {
        this.controlMigratorioVo = controlMigratorioVo;
    }

    public SesionVo getSesionVo() {
        return sesionVo;
    }

    public void setSesionVo(SesionVo sesionVo) {
        this.sesionVo = sesionVo;
    }

    public void setAuthorities(Collection<GrantedAuthorityCustom> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<GrantedAuthorityCustom> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
