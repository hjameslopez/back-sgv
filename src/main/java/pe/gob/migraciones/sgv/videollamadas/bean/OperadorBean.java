package pe.gob.migraciones.sgv.videollamadas.bean;

public class OperadorBean {
    private Integer nIdOperador;
    private String sNombreOperador;
    private boolean bActivo;
    private String sIdDependencia;
    private String sDni;
    private String sLogin;

    public String getsLogin() {
        return sLogin;
    }

    public void setsLogin(String sLogin) {
        this.sLogin = sLogin;
    }

    public String getsDni() {
        return sDni;
    }

    public void setsDni(String sDni) {
        this.sDni = sDni;
    }

    public Integer getnIdOperador() {
        return nIdOperador;
    }

    public void setnIdOperador(Integer nIdOperador) {
        this.nIdOperador = nIdOperador;
    }

    public String getsNombreOperador() {
        return sNombreOperador;
    }

    public void setsNombreOperador(String sNombreOperador) {
        this.sNombreOperador = sNombreOperador;
    }

    public boolean isbActivo() {
        return bActivo;
    }

    public void setbActivo(boolean bActivo) {
        this.bActivo = bActivo;
    }

    public String getsIdDependencia() {
        return sIdDependencia;
    }

    public void setsIdDependencia(String sIdDependencia) {
        this.sIdDependencia = sIdDependencia;
    }
}
