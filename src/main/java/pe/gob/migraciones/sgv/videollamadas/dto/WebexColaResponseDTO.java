package pe.gob.migraciones.sgv.videollamadas.dto;


import java.util.List;

import pe.gob.migraciones.sgv.videollamadas.bean.WebexSimVideColaBean;

public class WebexColaResponseDTO {
    private Integer nIdSimVideCola;
    private Integer sCode;
    private String sCodTicket;
    private String dReloj;
    private String sActivarTabCola;
    private List<WebexSimVideColaBean> ColaCall;
    private Integer NroOrden;
    private List<String> token;
    private String sOpeAsignado;
    private Long tiempoEspera;

    public Integer getnIdSimVideCola() {
        return nIdSimVideCola;
    }

    public Integer getsCode() {
        return sCode;
    }

    public void setsCode(Integer sCode) {
        this.sCode = sCode;
    }

    public String getsCodTicket() {
        return sCodTicket;
    }

    public void setsCodTicket(String sCodTicket) {
        this.sCodTicket = sCodTicket;
    }

    public String getdReloj() {
        return dReloj;
    }

    public void setdReloj(String dReloj) {
        this.dReloj = dReloj;
    }

    public void setsActivarTabCola(String sActivarTabCola) {
        this.sActivarTabCola = sActivarTabCola;
    }

    public String getsActivarTabCola() {
        return sActivarTabCola;
    }

    public List<WebexSimVideColaBean> getColaCall() {
        return ColaCall;
    }

    public void setColaCall(List<WebexSimVideColaBean> colaCall) {
        ColaCall = colaCall;
    }

    public Integer getNroOrden() {
        return NroOrden;
    }

    public void setNroOrden(Integer nroOrden) {
        NroOrden = nroOrden;
    }

    public void setnIdSimVideCola(Integer nIdSimVideCola) {
        this.nIdSimVideCola = nIdSimVideCola;
    }

    public List<String> getToken() {
        return token;
    }

    public void setToken(List<String> token) {
        this.token = token;
    }

    public String getsOpeAsignado() {
        return sOpeAsignado;
    }

    public void setsOpeAsignado(String sOpeAsignado) {
        this.sOpeAsignado = sOpeAsignado;
    }

    public Long getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(Long tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
}
