package pe.gob.migraciones.sgv.videollamadas.bean;

public class WebexAtencionBean {
    
    private Integer nIdSimVideCola;
    private String sIdPersona;
    private String sNombres;
    private String sTipTicket;
    private String sCodTicket;
    private String sOpeAsignado;
    private String nEstado;
    private String dFecCreado;
    private String dFecLlamada;
    private String nIdSesion;
    private String nEdad;
    
    private String sLicenciaAsignar;

    
    
    public String getsLicenciaAsignar() {
		return sLicenciaAsignar;
	}
	public void setsLicenciaAsignar(String sLicenciaAsignar) {
		this.sLicenciaAsignar = sLicenciaAsignar;
	}
	public Integer getnIdSimVideCola() {
		return nIdSimVideCola;
	}
	public void setnIdSimVideCola(Integer nIdSimVideCola) {
		this.nIdSimVideCola = nIdSimVideCola;
	}
	public String getsIdPersona() {
        return sIdPersona;
    }
    public void setsIdPersona(String sIdPersona) {
        this.sIdPersona = sIdPersona;
    }
    public String getsNombres() {
        return sNombres;
    }
    public void setsNombres(String sNombres) {
        this.sNombres = sNombres;
    }
    public String getsTipTicket() {
        return sTipTicket;
    }
    public void setsTipTicket(String sTipTicket) {
        this.sTipTicket = sTipTicket;
    }
    public String getsCodTicket() {
        return sCodTicket;
    }
    public void setsCodTicket(String sCodTicket) {
        this.sCodTicket = sCodTicket;
    }
    public String getsOpeAsignado() {
        return sOpeAsignado;
    }
    public void setsOpeAsignado(String sOpeAsignado) {
        this.sOpeAsignado = sOpeAsignado;
    }
    public String getnEstado() {
        return nEstado;
    }
    public void setnEstado(String nEstado) {
        this.nEstado = nEstado;
    }
    public String getdFecCreado() {
        return dFecCreado;
    }
    public void setdFecCreado(String dFecCreado) {
        this.dFecCreado = dFecCreado;
    }
    public String getnIdSesion() {
        return nIdSesion;
    }
    public void setnIdSesion(String nIdSesion) {
        this.nIdSesion = nIdSesion;
    }
    public String getnEdad() {
        return nEdad;
    }
    public void setnEdad(String nEdad) {
        this.nEdad = nEdad;
    }
    
    public String getdFecLlamada() {
		return dFecLlamada;
	}
	public void setdFecLlamada(String dFecLlamada) {
		this.dFecLlamada = dFecLlamada;
	}

   
	public WebexAtencionBean(Integer nIdSimVideCola, String sIdPersona, String sNombres,
			String sTipTicket, String sCodTicket, String sOpeAsignado, String nEstado, String dFecCreado,
			String dFecLlamada, String nIdSesion, String nEdad, String sLicenciaAsignar) {
		super();
		
		this.nIdSimVideCola = nIdSimVideCola;
		this.sIdPersona = sIdPersona;
		this.sNombres = sNombres;
		this.sTipTicket = sTipTicket;
		this.sCodTicket = sCodTicket;
		this.sOpeAsignado = sOpeAsignado;
		this.nEstado = nEstado;
		this.dFecCreado = dFecCreado;
		this.dFecLlamada = dFecLlamada;
		this.nIdSesion = nIdSesion;
		this.nEdad = nEdad;
		this.sLicenciaAsignar = sLicenciaAsignar;
	}
	public WebexAtencionBean() {
        super();
    }



}
