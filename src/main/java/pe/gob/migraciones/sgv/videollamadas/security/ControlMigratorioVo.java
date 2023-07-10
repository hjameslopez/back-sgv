package pe.gob.migraciones.sgv.videollamadas.security;

import java.util.Date;

public class ControlMigratorioVo {
    private String sIdTipoControlMigratorio;
    private Date dFechaTurnoFin;
    private Date dFechaTurnoInicio;
    private Integer nIdTurnoOperador;

    public String getsIdTipoControlMigratorio() {
        return sIdTipoControlMigratorio;
    }

    public void setsIdTipoControlMigratorio(String sIdTipoControlMigratorio) {
        this.sIdTipoControlMigratorio = sIdTipoControlMigratorio;
    }

    public Date getdFechaTurnoFin() {
        return dFechaTurnoFin;
    }

    public void setdFechaTurnoFin(Date dFechaTurnoFin) {
        this.dFechaTurnoFin = dFechaTurnoFin;
    }

    public Date getdFechaTurnoInicio() {
        return dFechaTurnoInicio;
    }

    public void setdFechaTurnoInicio(Date dFechaTurnoInicio) {
        this.dFechaTurnoInicio = dFechaTurnoInicio;
    }

    public Integer getnIdTurnoOperador() {
        return nIdTurnoOperador;
    }

    public void setnIdTurnoOperador(Integer nIdTurnoOperador) {
        this.nIdTurnoOperador = nIdTurnoOperador;
    }
}
