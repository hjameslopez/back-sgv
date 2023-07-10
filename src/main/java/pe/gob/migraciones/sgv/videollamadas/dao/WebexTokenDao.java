package pe.gob.migraciones.sgv.videollamadas.dao;

import java.sql.SQLException;
import java.util.List;

import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.SesionWebBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexAtencionBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexCorrelativoBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexListaNegraBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexMensajeInicialBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexSimVideColaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexTokenBean;

public interface WebexTokenDao {
    List<WebexTokenBean> findToken(String docidentidad);
    String insToken(WebexTokenBean webexTokenBean);
    boolean archivaToken(String token);
    List<SesionWebBean> findUuid(String nidsesion);
    List<WebexListaNegraBean> isUserBlocked(String nidsesion);
    List<WebexMensajeInicialBean> MensajeInicial(String sTipo);
    List<WebexSimVideColaBean> isUserInCall(String uid);
    //void encolar(WebexSimVideColaBean webexSimVideColaBean);
    List<WebexCorrelativoBean> obtenerCorrelativo(String sTipTicket);
    boolean updateTablaTickets(Integer nCorrelativo, Integer nIdSimVideGenTic);
    String activarTabColaCall();
    List<WebexSimVideColaBean> obtenerColaCall();
    List<WebexSimVideColaBean> obtenerColaEspera();
    List<WebexSimVideColaBean> isRegistroInTurno(Integer nIdSimVideCola);
    List<WebexSimVideColaBean> isUserInCola(String uid);
    List<WebexSimVideColaBean> isUserInTurno(String sCodTicket);

    List<WebexSimVideColaBean> obtenerOpeAsignado(Integer nIdSimVideCola);
    List<WebexSimVideColaBean> hayRegistroInTurno();
    boolean updateLlamar(Integer nIdSimVideCola);
    boolean updateCancelarTurno(Integer nIdSimVideCola);
    List<WebexSimVideColaBean> getColaEstados(Integer bActivo);
    //public int actualizarAsignarOperador(String operador,String ticket);
    public WebexAtencionBean buscaAtencionTicket(Integer nIdSimVideCola);
    public List<WebexAtencionBean> buscaListAtencionTicket(Integer nIdSimVideCola);
    public int actualizarTerminarLlamada(String operador,Integer nIdSimVideCola);
    public List<WebexSimVideColaBean> getColaFiltros(String sOpeAsignado, String fechaInicial, String fechaFinal);
    public List<WebexSimVideColaBean> getColaEstado2(Integer bActivo);
    public WebexSimVideColaBean validaOperador(Integer nIdSimVideCola);
    //public String PrimerSegundoDelDia();
    //public String UltimoSegundoDelDia();
    public WebexAtencionBean asignaOperador(String correo, Integer nIdSimVideCola) throws SQLException;
    public String obtenerLicencia(Integer nIdOperador);
	public LicenciaBean licenciaAsignada(Integer idUsuario);
}
