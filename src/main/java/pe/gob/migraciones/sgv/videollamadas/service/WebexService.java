package pe.gob.migraciones.sgv.videollamadas.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadoresBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexAtencionBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexSimVideColaBean;
import pe.gob.migraciones.sgv.videollamadas.dto.ResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.dto.WebexGuestResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.dto.WebexOperadorResponseDTO;

public interface WebexService {
    //ResponseDTO<List<WebexOperadorResponseDTO>> getOperador();

    //ResponseDTO<List<WebexGuestResponseDTO>> getToken(Integer nIdSimVideCola, String guest, String nidsesion);

    List<WebexSimVideColaBean> getColaEstados(Integer bActivo) throws Exception;

    public Integer validaOperador(Integer nIdSimVideCola, Authentication authentication) throws Exception;

    WebexAtencionBean updateAsignarOperador(WebexAtencionBean webexAtencionBean) throws Exception;

    WebexAtencionBean updateTerminarLlamada(WebexAtencionBean webexAtencionBean) throws Exception;

    public List<WebexAtencionBean> buscaListAtencionTicket(Integer nIdSimVideCola);

    public List<WebexSimVideColaBean> getColaEstado2(Integer bActivo)throws Exception;

    void ActualizarCola();

    public List<LicenciaBean> listLicencias();
    
    public List<OperadoresBean> listOperadores();

    public List<WebexSimVideColaBean> getColaFiltros(String sOpeAsignado, String fechaInicial, String fechaFinal)throws Exception;

    public Integer validaCerrar(Integer nIdSimVideCola, Authentication authentication)throws Exception;
    
    public String extraerEstado(Integer nIdSimVideCola)throws Exception;

	public LicenciaBean licenciaAsignada(Authentication authentication);
	
	public OperadoresBean verificaNuevoOperador(String sLogin) throws Exception;
	
	public OperadoresBean getOperador(String sLogin);
	
	public ResponseDTO<Integer> notificarRegistrarOperador(OperadoresBean operadoresBean);
	
	public List<OperadoresBean> listaCbxOperadores();

	
}