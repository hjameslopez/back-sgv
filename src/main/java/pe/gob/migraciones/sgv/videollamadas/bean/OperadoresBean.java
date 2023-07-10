package pe.gob.migraciones.sgv.videollamadas.bean;


import java.time.LocalDateTime;

public class OperadoresBean {

    private Integer nIdOperador;
    private String sLogin;
    private String sNombre;
    private LocalDateTime dFechaHoraAud;
    private String bActivo;
	public Integer getnIdOperador() {
		return nIdOperador;
	}
	public void setnIdOperador(Integer nIdOperador) {
		this.nIdOperador = nIdOperador;
	}
	public String getsLogin() {
		return sLogin;
	}
	public void setsLogin(String sLogin) {
		this.sLogin = sLogin;
	}
	public String getsNombre() {
		return sNombre;
	}
	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}
	public LocalDateTime getdFechaHoraAud() {
		return dFechaHoraAud;
	}
	public void setdFechaHoraAud(LocalDateTime dFechaHoraAud) {
		this.dFechaHoraAud = dFechaHoraAud;
	}
	public String getbActivo() {
		return bActivo;
	}
	public void setbActivo(String bActivo) {
		this.bActivo = bActivo;
	}
	public OperadoresBean(Integer nIdOperador, String sLogin, String sNombre, LocalDateTime dFechaHoraAud,
			String bActivo) {
		super();
		this.nIdOperador = nIdOperador;
		this.sLogin = sLogin;
		this.sNombre = sNombre;
		this.dFechaHoraAud = dFechaHoraAud;
		this.bActivo = bActivo;
	}
	public OperadoresBean() {
		super();
	}
    
    
	

}
