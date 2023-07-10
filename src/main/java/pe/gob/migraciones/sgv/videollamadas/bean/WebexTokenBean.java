package pe.gob.migraciones.sgv.videollamadas.bean;

import java.util.Date;

public class WebexTokenBean {
    private Integer nIdSimTokWebex;
    private String sCodIdentificador;
    private String sAccToken;
    private Date dFecFinAccToken;
    private String sRefToken;
    private Date dFecFinRefToken;

    public Integer getnIdSimTokWebex() {
        return nIdSimTokWebex;
    }

    public void setnIdSimTokWebex(Integer nIdSimTokWebex) {
        this.nIdSimTokWebex = nIdSimTokWebex;
    }

    public String getsCodIdentificador() {
        return sCodIdentificador;
    }

    public void setsCodIdentificador(String sCodIdentificador) {
        this.sCodIdentificador = sCodIdentificador;
    }

    public String getsAccToken() {
        return sAccToken;
    }

    public void setsAccToken(String sAccToken) {
        this.sAccToken = sAccToken;
    }

    public Date getdFecFinAccToken() {
        return dFecFinAccToken;
    }

    public void setdFecFinAccToken(Date dFecFinAccToken) {
        this.dFecFinAccToken = dFecFinAccToken;
    }

    public String getsRefToken() {
        return sRefToken;
    }

    public void setsRefToken(String sRefToken) {
        this.sRefToken = sRefToken;
    }

    public Date getdFecFinRefToken() {
        return dFecFinRefToken;
    }

    public void setdFecFinRefToken(Date dFecFinRefToken) {
        this.dFecFinRefToken = dFecFinRefToken;
    }
}
