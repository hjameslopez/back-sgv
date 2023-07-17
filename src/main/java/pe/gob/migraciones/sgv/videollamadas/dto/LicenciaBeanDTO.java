package pe.gob.migraciones.sgv.videollamadas.dto;


import java.time.LocalDateTime;

public class LicenciaBeanDTO {

    private Integer nIdLicencia;
    private String sLicencia;
    private String sLogin;
    private String sCorreo;
    private String sContraseña;
    private LocalDateTime dFechaHoraAud;
    private String sNombre;
    private String bActivo;
    private Integer nIdOperador;
    private Integer idOpeAntiguo;
    private Integer idOpeNuevo;
    
    
	public LicenciaBeanDTO() {
		super();
	}
	
	public LicenciaBeanDTO(Integer nIdLicencia, String sLicencia, String sLogin, String sCorreo, String sContraseña,
			LocalDateTime dFechaHoraAud, String sNombre, String bActivo, Integer nIdOperador, Integer idOpeAntiguo,
			Integer idOpeNuevo) {
		super();
		this.nIdLicencia = nIdLicencia;
		this.sLicencia = sLicencia;
		this.sLogin = sLogin;
		this.sCorreo = sCorreo;
		this.sContraseña = sContraseña;
		this.dFechaHoraAud = dFechaHoraAud;
		this.sNombre = sNombre;
		this.bActivo = bActivo;
		this.nIdOperador = nIdOperador;
		this.idOpeAntiguo = idOpeAntiguo;
		this.idOpeNuevo = idOpeNuevo;
	}
	
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
	public Integer getnIdOperador() {
		return nIdOperador;
	}
	public void setnIdOperador(Integer nIdOperador) {
		this.nIdOperador = nIdOperador;
	}
	public Integer getIdOpeAntiguo() {
		return idOpeAntiguo;
	}
	public void setIdOpeAntiguo(Integer idOpeAntiguo) {
		this.idOpeAntiguo = idOpeAntiguo;
	}
	public Integer getIdOpeNuevo() {
		return idOpeNuevo;
	}
	public void setIdOpeNuevo(Integer idOpeNuevo) {
		this.idOpeNuevo = idOpeNuevo;
	}
    
	    
    
}
