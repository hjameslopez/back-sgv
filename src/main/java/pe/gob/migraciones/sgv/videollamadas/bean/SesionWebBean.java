package pe.gob.migraciones.sgv.videollamadas.bean;

import java.time.LocalDateTime;

public class SesionWebBean {
    private long nIdSesionWeb;
    private String uIdPersona;
    private LocalDateTime dFechaHoraInicio;
    private LocalDateTime dFechaHoraFin;
    private Boolean bFinalizado;
    private boolean bActivo;
    private LocalDateTime dFechaHoraAud;
    private String sIdTipoDocumento;
    private String sNumeroDocumento;
    private String sCodCitaWeb;

    public SesionWebBean() {
    }

    public long getnIdSesionWeb() {
        return nIdSesionWeb;
    }

    public void setnIdSesionWeb(long nIdSesionWeb) {
        this.nIdSesionWeb = nIdSesionWeb;
    }

    public String getuIdPersona() {
        return uIdPersona;
    }

    public void setuIdPersona(String uIdPersona) {
        this.uIdPersona = uIdPersona;
    }

    public LocalDateTime getdFechaHoraInicio() {
        return dFechaHoraInicio;
    }

    public void setdFechaHoraInicio(LocalDateTime dFechaHoraInicio) {
        this.dFechaHoraInicio = dFechaHoraInicio;
    }

    public LocalDateTime getdFechaHoraFin() {
        return dFechaHoraFin;
    }

    public void setdFechaHoraFin(LocalDateTime dFechaHoraFin) {
        this.dFechaHoraFin = dFechaHoraFin;
    }

    public Boolean getbFinalizado() {
        return bFinalizado;
    }

    public void setbFinalizado(Boolean bFinalizado) {
        this.bFinalizado = bFinalizado;
    }

    public boolean isbActivo() {
        return bActivo;
    }

    public void setbActivo(boolean bActivo) {
        this.bActivo = bActivo;
    }

    public LocalDateTime getdFechaHoraAud() {
        return dFechaHoraAud;
    }

    public void setdFechaHoraAud(LocalDateTime dFechaHoraAud) {
        this.dFechaHoraAud = dFechaHoraAud;
    }

    public String getsIdTipoDocumento() {
        return sIdTipoDocumento;
    }

    public void setsIdTipoDocumento(String sIdTipoDocumento) {
        this.sIdTipoDocumento = sIdTipoDocumento;
    }

    public String getsNumeroDocumento() {
        return sNumeroDocumento;
    }

    public void setsNumeroDocumento(String sNumeroDocumento) {
        this.sNumeroDocumento = sNumeroDocumento;
    }

    public String getsCodCitaWeb() { return sCodCitaWeb; }

    public void setsCodCitaWeb(String sCodCitaWeb) { this.sCodCitaWeb = sCodCitaWeb; }

    @Override
    public String toString() {
        return "SesionWebBean{" +
                "nIdSesionWeb=" + nIdSesionWeb +
                ", uIdPersona='" + uIdPersona + '\'' +
                ", dFechaHoraInicio=" + dFechaHoraInicio +
                ", dFechaHoraFin=" + dFechaHoraFin +
                ", bFinalizado=" + bFinalizado +
                ", bActivo=" + bActivo +
                ", dFechaHoraAud=" + dFechaHoraAud +
                ", sIdTipoDocumento='" + sIdTipoDocumento + '\'' +
                ", sNumeroDocumento='" + sNumeroDocumento + '\'' +
                ", sCodCitaWeb='" + sCodCitaWeb + '\'' +
                '}';
    }
}
