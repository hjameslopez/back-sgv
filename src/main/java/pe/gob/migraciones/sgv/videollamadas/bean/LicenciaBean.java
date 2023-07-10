package pe.gob.migraciones.sgv.videollamadas.bean;


import java.time.LocalDateTime;

public class LicenciaBean {

    private Integer nIdLicencia;
    private String sLicencia;
    private String sLogin;
    private String sCorreo;
    private String sContraseña;
    private LocalDateTime dFechaHoraAud;
    private String sNombre;
    private String bActivo;

    
    public Integer getnIdLicencia() {
		return nIdLicencia;
	}


	public void setnIdLicencia(Integer nIdLicencia) {
		this.nIdLicencia = nIdLicencia;
	}


	public String getsLicencia() {
		return sLicencia;
	}


	public void setsLicencia(String sLicencia) {
		this.sLicencia = sLicencia;
	}


	public String getsLogin() {
		return sLogin;
	}


	public void setsLogin(String sLogin) {
		this.sLogin = sLogin;
	}


	public String getsCorreo() {
		return sCorreo;
	}


	public void setsCorreo(String sCorreo) {
		this.sCorreo = sCorreo;
	}


	public String getsContraseña() {
		return sContraseña;
	}


	public void setsContraseña(String sContraseña) {
		this.sContraseña = sContraseña;
	}


	public LocalDateTime getdFechaHoraAud() {
		return dFechaHoraAud;
	}


	public void setdFechaHoraAud(LocalDateTime dFechaHoraAud) {
		this.dFechaHoraAud = dFechaHoraAud;
	}


	public String getsNombre() {
		return sNombre;
	}


	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}


	public String getbActivo() {
		return bActivo;
	}


	public void setbActivo(String bActivo) {
		this.bActivo = bActivo;
	}


	public LicenciaBean(Integer nIdLicencia, String sLicencia, String sLogin, String sCorreo, String sContraseña,
			LocalDateTime dFechaHoraAud, String sNombre, String bActivo) {
		super();
		this.nIdLicencia = nIdLicencia;
		this.sLicencia = sLicencia;
		this.sLogin = sLogin;
		this.sCorreo = sCorreo;
		this.sContraseña = sContraseña;
		this.dFechaHoraAud = dFechaHoraAud;
		this.sNombre = sNombre;
		this.bActivo = bActivo;
	}


	public LicenciaBean() {
        
    }
}
