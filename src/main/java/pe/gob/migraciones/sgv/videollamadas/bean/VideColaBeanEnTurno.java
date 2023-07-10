package pe.gob.migraciones.sgv.videollamadas.bean;

import java.sql.Timestamp;

public class VideColaBeanEnTurno {

    private Integer nIdSimVideCola;
    private Timestamp dFecEnTurno;

    public Integer getnIdSimVideCola() {
        return nIdSimVideCola;
    }

    public void setnIdSimVideCola(Integer nIdSimVideCola) {
        this.nIdSimVideCola = nIdSimVideCola;
    }

    public Timestamp getdFecEnTurno() {
        return dFecEnTurno;
    }

    public void setdFecEnTurno(Timestamp dFecEnTurno) {
        this.dFecEnTurno = dFecEnTurno;
    }
}
