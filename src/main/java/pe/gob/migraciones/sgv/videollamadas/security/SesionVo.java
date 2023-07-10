package pe.gob.migraciones.sgv.videollamadas.security;

public class SesionVo {
    private String sIdDependencia;
    private Integer nIdSesion;
    private Integer nIdOperador;
    private Integer nIdEstacion;
    private String sNombreEstacion;
    private String sNombreOperador;
    private String sModulo;
   
	public String getsModulo() {
        return sModulo;
    }

    public void setsModulo(String sModulo) {
        this.sModulo = sModulo;
    }

    public String getsNombreEstacion() {
        return sNombreEstacion;
    }

    public void setsNombreEstacion(String sNombreEstacion) {
        this.sNombreEstacion = sNombreEstacion;
    }

    public String getsNombreOperador() {
        return sNombreOperador;
    }

    public void setsNombreOperador(String sNombreOperador) {
        this.sNombreOperador = sNombreOperador;
    }

    public String getsIdDependencia() {
        return sIdDependencia;
    }

    public void setsIdDependencia(String sIdDependencia) {
        this.sIdDependencia = sIdDependencia;
    }

    public Integer getnIdSesion() {
        return nIdSesion;
    }

    public void setnIdSesion(Integer nIdSesion) {
        this.nIdSesion = nIdSesion;
    }

    public Integer getnIdOperador() {
        return nIdOperador;
    }

    public void setnIdOperador(Integer nIdOperador) {
        this.nIdOperador = nIdOperador;
    }

    public Integer getnIdEstacion() {
        return nIdEstacion;
    }

    public void setnIdEstacion(Integer nIdEstacion) {
        this.nIdEstacion = nIdEstacion;
    }
}
