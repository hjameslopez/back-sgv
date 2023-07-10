package pe.gob.migraciones.sgv.videollamadas.bean;

import java.io.Serializable;

public class OperadorWebexBean implements Serializable {
    private Integer nIdOperadorWebex;
    private String sOperador;
    private String sEstado;
    private Integer nIdSesion;
    private boolean bActivo;

    public Integer getnIdOperadorWebex() {
        return nIdOperadorWebex;
    }

    public void setnIdOperadorWebex(Integer nIdOperadorWebex) {
        this.nIdOperadorWebex = nIdOperadorWebex;
    }

    public String getsOperador() {
        return sOperador;
    }

    public void setsOperador(String sOperador) {
        this.sOperador = sOperador;
    }

    public String getsEstado() {
        return sEstado;
    }

    public void setsEstado(String sEstado) {
        this.sEstado = sEstado;
    }

    public Integer getnIdSesion() {
        return nIdSesion;
    }

    public void setnIdSesion(Integer nIdSesion) {
        this.nIdSesion = nIdSesion;
    }

    public boolean isbActivo() {
        return bActivo;
    }

    public void setbActivo(boolean bActivo) {
        this.bActivo = bActivo;
    }
}
