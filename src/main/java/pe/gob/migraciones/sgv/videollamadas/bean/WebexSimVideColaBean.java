package pe.gob.migraciones.sgv.videollamadas.bean;

import java.time.LocalDateTime;

public class WebexSimVideColaBean {
    private Integer nIdSimVideCola;
    private String sIdPersona;
    private String sNombres;
    private String sTipTicket;
    private String sCodTicket;
    private LocalDateTime dFecCita;
    private String sOpeAsignado;
    private Integer nEstado;
    private LocalDateTime dFecCreado;
    private LocalDateTime dFecEnTurno;
    private LocalDateTime dFecLlamada;
    private LocalDateTime dFecHoraAud;
    private Integer nIdSesion;
    private String nEdad;
    private String nombreOperador;
    private String sNombre;
    
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
	public LocalDateTime getdFecCita() {
		return dFecCita;
	}
	public void setdFecCita(LocalDateTime dFecCita) {
		this.dFecCita = dFecCita;
	}
	public String getsOpeAsignado() {
		return sOpeAsignado;
	}
	public void setsOpeAsignado(String sOpeAsignado) {
		this.sOpeAsignado = sOpeAsignado;
	}
	public Integer getnEstado() {
		return nEstado;
	}
	public void setnEstado(Integer nEstado) {
		this.nEstado = nEstado;
	}
	public LocalDateTime getdFecCreado() {
		return dFecCreado;
	}
	public void setdFecCreado(LocalDateTime dFecCreado) {
		this.dFecCreado = dFecCreado;
	}
	public LocalDateTime getdFecEnTurno() {
		return dFecEnTurno;
	}
	public void setdFecEnTurno(LocalDateTime dFecEnTurno) {
		this.dFecEnTurno = dFecEnTurno;
	}
	public LocalDateTime getdFecLlamada() {
		return dFecLlamada;
	}
	public void setdFecLlamada(LocalDateTime dFecLlamada) {
		this.dFecLlamada = dFecLlamada;
	}
	public LocalDateTime getdFecHoraAud() {
		return dFecHoraAud;
	}
	public void setdFecHoraAud(LocalDateTime dFecHoraAud) {
		this.dFecHoraAud = dFecHoraAud;
	}
	public Integer getnIdSesion() {
		return nIdSesion;
	}
	public void setnIdSesion(Integer nIdSesion) {
		this.nIdSesion = nIdSesion;
	}
	public String getnEdad() {
		return nEdad;
	}
	public void setnEdad(String nEdad) {
		this.nEdad = nEdad;
	}
	public String getNombreOperador() {
		return nombreOperador;
	}
	public void setNombreOperador(String nombreOperador) {
		this.nombreOperador = nombreOperador;
	}
	public String getsNombre() {
		return sNombre;
	}
	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}
	
	public WebexSimVideColaBean() {
		super();
	}
	
	public WebexSimVideColaBean(Integer nIdSimVideCola, String sIdPersona, String sNombres, String sTipTicket,
			String sCodTicket, LocalDateTime dFecCita, String sOpeAsignado, Integer nEstado, LocalDateTime dFecCreado,
			LocalDateTime dFecEnTurno, LocalDateTime dFecLlamada, LocalDateTime dFecHoraAud, Integer nIdSesion,
			String nEdad, String nombreOperador, String sNombre) {
		super();
		this.nIdSimVideCola = nIdSimVideCola;
		this.sIdPersona = sIdPersona;
		this.sNombres = sNombres;
		this.sTipTicket = sTipTicket;
		this.sCodTicket = sCodTicket;
		this.dFecCita = dFecCita;
		this.sOpeAsignado = sOpeAsignado;
		this.nEstado = nEstado;
		this.dFecCreado = dFecCreado;
		this.dFecEnTurno = dFecEnTurno;
		this.dFecLlamada = dFecLlamada;
		this.dFecHoraAud = dFecHoraAud;
		this.nIdSesion = nIdSesion;
		this.nEdad = nEdad;
		this.nombreOperador = nombreOperador;
		this.sNombre = sNombre;
	}
    
    

}
