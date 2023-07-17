package pe.gob.migraciones.sgv.videollamadas.service.imp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pe.gob.migraciones.sgv.videollamadas.bean.*;
import pe.gob.migraciones.sgv.videollamadas.dao.LicenciaDao;
import pe.gob.migraciones.sgv.videollamadas.dao.OperadorDao;
import pe.gob.migraciones.sgv.videollamadas.dao.WebexTokenDao;
import pe.gob.migraciones.sgv.videollamadas.dao.WebexTokenDaoCron;
import pe.gob.migraciones.sgv.videollamadas.dto.ResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.dto.WebexColaResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.dto.WebexGuestResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.dto.WebexOperadorResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.security.TokenJwtClientCustom;
import pe.gob.migraciones.sgv.videollamadas.security.UserDetailsCustom;
import pe.gob.migraciones.sgv.videollamadas.service.WebexService;
import pe.gob.migraciones.sgv.videollamadas.util.Constantes;
import pe.gob.migraciones.sgv.videollamadas.util.Utilitario;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WebexServiceImpl implements WebexService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebexTokenDao webexTokenDao;
    @Autowired
    private WebexTokenDaoCron webexTokenDaoCron;
    @Autowired
    private TokenJwtClientCustom tokenJwtCustom;

    @Autowired
    OperadorDao operadorDao;
    @Autowired
    LicenciaDao licenciaDao;
   
    private String refreshTokenIntegracion(String refresh_token) {
        // Jalo el client_id y el client_Secret del metodo integracion_credenciales
        ArrayList<String> credenciales = integracion_credenciales();
        String client_id = credenciales.get(0);
        String client_secret = credenciales.get(1);

        // Ahora vamos a cambiar de token:
        String url = "https://webexapis.com/v1/access_token";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "refresh_token");
        map.put("client_id", client_id);
        map.put("client_secret", client_secret);
        map.put("refresh_token", refresh_token);
        ResponseEntity<String> response = restTemplate.postForEntity(url, map, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Convierto el responseStrBuilder a un JsonObject
            JsonObject jsonObj = new Gson().fromJson(response.getBody().toString(), JsonObject.class);

            // Extraigo el access_token y su vigencia
            String n_access_token_temp = jsonObj.get("access_token").toString();
            String n_access_token = n_access_token_temp.replace("\"", "");
            int n_expiration = Integer.parseInt(jsonObj.get("expires_in").toString());

            // Extraigo el refresh_token y su vigencia
            String n_refresh_token_temp = jsonObj.get("refresh_token").toString();
            String n_refresh_token = n_refresh_token_temp.replace("\"", "");
            int n_expiration_r = Integer.parseInt(jsonObj.get("refresh_token_expires_in").toString());

            // Sumo al momento de ahora el string expiration
            Date ahora = new Date();
            Calendar now = Calendar.getInstance();
            now.setTime(ahora);
            // Añadimos la expiracion
            now.add(Calendar.SECOND, n_expiration);
            // Ahora convertimos a date
            Date n_fecha_fin = now.getTime();

            // Sumo al momento de ahora el string r_expiration
            Date ahora1 = new Date();
            Calendar now1 = Calendar.getInstance();
            now1.setTime(ahora1);
            // Añadimos la expiracion del refresh
            now1.add(Calendar.SECOND, n_expiration_r);
            // Ahora convertimos a date
            Date n_fecha_fin_r = now1.getTime();

            // Registrar Token
            WebexTokenBean webexTokenBean = new WebexTokenBean();
            webexTokenBean.setsCodIdentificador("INTEGRACION");
            webexTokenBean.setsAccToken(n_access_token);
            webexTokenBean.setdFecFinAccToken(n_fecha_fin);
            webexTokenBean.setsRefToken(n_refresh_token);
            webexTokenBean.setdFecFinRefToken(n_fecha_fin_r);

            // Registrar token y obtener ID
            String idToken = webexTokenDao.insToken(webexTokenBean);
            if (idToken == null)
                System.out.println("No se pudo registrar el token de integracion");

            return n_access_token;

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return "1";

    }
/*
    @Override
    public ResponseDTO<List<WebexGuestResponseDTO>> getToken(Integer nIdSimVideCola, String guest, String nidsesion) {
        ResponseDTO response = null;
        WebexColaResponseDTO message = new WebexColaResponseDTO();

        List<SesionWebBean> sesionWebBeanList = webexTokenDao.findUuid(nidsesion);
        SesionWebBean uuid_temp = sesionWebBeanList.get(0);
        String uid = uuid_temp.getuIdPersona();

        // Vamos a extraer el operador asignado que ha sido por el Job
        // al registro en la tabla SimVideCola
        // para retornarlo al front
        List<WebexSimVideColaBean> getOpeAsignado = webexTokenDao.obtenerOpeAsignado(nIdSimVideCola);
        if (getOpeAsignado.isEmpty()) {
            logger.warn("WARN -> No se pudo obtener el List<WebexSimVideColaBean> getOpeAsignado");
        }
        WebexSimVideColaBean ope_temp = getOpeAsignado.get(0);
        String sOpeAsignado = ope_temp.getsOpeAsignado();
        message.setsOpeAsignado(sOpeAsignado);

        List<WebexTokenBean> webexTokenBeanList = webexTokenDao.findToken(uid);

        if (webexTokenBeanList.isEmpty() == true) {
            // Si no tiene token hay que generarle
            // String token = null;
            try {
                // token = generaToken(guest,uid);

                List<String> token = Arrays.asList(generaToken(guest, uid));
                message.setToken(token);
                response = Utilitario.getInstancia().responseOK(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // return token;
        } else {
            // Extraigo el String con el token de invitado
            WebexTokenBean access_token_temp = webexTokenBeanList.get(0);
            String token = access_token_temp.getsAccToken();
            Date fechafin = access_token_temp.getdFecFinAccToken();

            Date ahora = new Date();

            // Si el access_token ya ha sobrepasado la fecha límite
            if (ahora.after(fechafin)) {
                // Archivo el token vencido
                String archivado = archivaToken(token);
                if (archivado == "1") {
                    // genero otro access token
                    // String ntoken = null;
                    try {
                        List<String> ntoken = Arrays.asList(generaToken(guest, uid));
                        message.setToken(ntoken);
                        response = Utilitario.getInstancia().responseOK(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // return ntoken;
                } else {
                    logger.warn("WARN-> No se pudo archivar el token");
                }

            } else {
                // Envío el access_token solicitado
                // return token;
                List<String> otoken = Arrays.asList(token);
                message.setToken(otoken);
                response = Utilitario.getInstancia().responseOK(message);
            }

        }

        // Antes de enviar el response al Front vamos a cambiar de estado al registro
        Boolean PonerEstado4 = webexTokenDao.updateLlamar(nIdSimVideCola);
        if (!PonerEstado4) {
            logger.warn("Error al poner en estado 4 al registro: " + nIdSimVideCola);
        }

        return response;
    }
*/
    private String archivaToken(String token) {
        String output;
        Boolean archivaToken = webexTokenDao.archivaToken(token);

        if (archivaToken) {
            output = "1";
        } else {
            output = "0";
        }

        return output;
    }
/*
    
*/
    private ArrayList<String> integracion_credenciales() {

        String client_id = Constantes.WEBEX.client_id;
        String client_secret = Constantes.WEBEX.client_secret;

        ArrayList<String> arlist = new ArrayList<String>();
        arlist.add(0, client_id);
        arlist.add(1, client_secret);

        return arlist;
    }


    public Integer GetNroOperadores() {

        JsonObject listaope = new JsonObject();

        // Obtengo un token de integracion válido
        String token = GetTokenIntegracion();

        // Consumo el servicio de Webex para Obtener un JSON con info de mi organizacion
        String url = "https://webexapis.com/v1/people";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + token);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response!=null && response.getStatusCode() == HttpStatus.OK) {

            // Convierto el response a un JsonObject
            JsonObject jsonObj = new Gson().fromJson(response.getBody().toString(), JsonObject.class);
            // Solo me interesa el sub objeto items
            JsonArray Operadores = jsonObj.getAsJsonArray("items");

            // Armamos un objeto con los operadores y sus estados

            for (int i = 0; i < Operadores.size(); i++) {
                if (!(Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0)
                        .getAsString().equals("jlavalle@migraciones.gob.pe")
                        || Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0)
                        .getAsString().equals("migraperuconf01@migraciones.gob.pe"))) {
                    listaope.addProperty(
                            Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0)
                                    .getAsString(),
                            Operadores.getAsJsonArray().get(i).getAsJsonObject().get("status").getAsString());
                }
            }

        } else {
            logger.warn("Request Failed");
            logger.warn(String.valueOf(response.getStatusCode()));
        }

        System.out.println(listaope.toString());
        return listaope.size();
    }

    public String GetTokenIntegracion() {

        String docIdentidad = "INTEGRACION";
        List<WebexTokenBean> webexTokenBeanList = webexTokenDao.findToken(docIdentidad);

        // Obtengo el token de INTEGRACION, su fecha de vigencia y su refresh_token
        WebexTokenBean access_token_temp = webexTokenBeanList.get(0);
        String token = access_token_temp.getsAccToken();
        Date fechafin = access_token_temp.getdFecFinAccToken();
        String refresh_token = access_token_temp.getsRefToken();

        // La fecha de hoy
        Date ahora = new Date();

        // Si el access_token al día de hoy ya ha sobrepasado la fecha límite
        if (ahora.after(fechafin)) {
            // Archivo el token vencido
            String archivado = archivaToken(token);
            if (archivado == "1") {
                // Genero otro access token
                String guest = "INTEGRACION";
                token = refreshTokenIntegracion(refresh_token);
            } else {
                System.out.println("WARN-> No se pudo archivar el token");
            }
        }

        return token;
    }
    
    @Override
    public List<WebexSimVideColaBean> getColaEstados(Integer bActivo) {
        ActualizarCola();       
        
        try {
            return webexTokenDao.getColaEstados(bActivo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
    }

    @Override
    public List<WebexSimVideColaBean> getColaEstado2(Integer bActivo) {

        ActualizarCola();       
        
        try {
        	return webexTokenDao.getColaEstado2(bActivo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

    }
    @Override
    public WebexAtencionBean updateAsignarOperador(WebexAtencionBean webex, Authentication authentication) throws Exception {
    	UserDetailsCustom user = (UserDetailsCustom) authentication.getPrincipal();
        OperadorBean operadorBean = operadorDao.getOperador(user.getSesionVo().getnIdOperador());
    	String correo = webex.getsLicenciaAsignar();
        
        WebexAtencionBean objSalida = null;
        // valida si no tiene operador asignado => se puede actualizar
        if (webex.getsOpeAsignado() == null) {
        	objSalida = webexTokenDao.asignaOperador(correo, webex.getnIdSimVideCola(), operadorBean.getsLogin());
        } else {
        	objSalida = webexTokenDao.buscaAtencionTicket(webex.getnIdSimVideCola());
        }
        return objSalida;
    }

    @Override
    public List<WebexAtencionBean> buscaListAtencionTicket(Integer nIdSimVideCola) {
        return webexTokenDao.buscaListAtencionTicket(nIdSimVideCola);
    }

    @Override
    public Integer validaOperador(Integer nIdSimVideCola, Authentication authentication) {

        try {
            UserDetailsCustom user = (UserDetailsCustom) authentication.getPrincipal();
            String correo = webexTokenDao.obtenerLicencia(user.getSesionVo().getnIdOperador()).trim();
            WebexSimVideColaBean top1ticketAtencion = webexTokenDao.validaOperador(nIdSimVideCola);
            String licencia = top1ticketAtencion.getsOpeAsignado().trim();
            int coincidencia = 333;

            if(correo.equals(licencia)) {
                coincidencia =1;
            }
            
            System.out.println("validaOperador:correo....."+correo+"......");
            System.out.println("validaOperador:licencia..."+licencia+"......");
            return coincidencia;
		} catch (Exception e) {
			//0 Es error con la licencia...
			return 0;
		}
    }

    @Override
    public Integer validaCerrar(Integer nIdSimVideCola, Authentication authentication) {
        try {
            UserDetailsCustom user = (UserDetailsCustom) authentication.getPrincipal();
            String correo = webexTokenDao.obtenerLicencia(user.getSesionVo().getnIdOperador());
            WebexSimVideColaBean top1ticketAtencion = webexTokenDao.validaOperador(nIdSimVideCola);
            String licencia = top1ticketAtencion.getsOpeAsignado();
            if(licencia!=null) {licencia=licencia.trim();}
            String estadoOpe = obtenerEstadoOpe(correo);
            System.out.println("Estado del operador "+ correo+" es "+estadoOpe);
            int CerrarOK = 111;   

            if(correo.equals(licencia)&&top1ticketAtencion.getnEstado()==3) {
                CerrarOK = 222;
                System.out.println("No puedes cerrar, esperando aceptación del ciudadano...");
            }
            if(correo.equals(licencia)&&estadoOpe.equals("call")) {
                CerrarOK = 333;
                System.out.println("No puede cerrar, la llamada no ha terminado...");
            }
            if(correo.equals(licencia)&&(top1ticketAtencion.getdFecLlamada()!=null)&&!estadoOpe.equals("call")) {
                CerrarOK = 555;
                System.out.println("Seleccionar el botón \"Terminar Llamada\"...");
            }
            if(correo.equals(licencia)&&top1ticketAtencion.getnEstado()!=3&&(top1ticketAtencion.getdFecLlamada()==null)) {
                CerrarOK = 444;
                System.out.println("Seleccionar el botón \"Cerrar\"...");
            }
            
            System.out.println("cerrar-correo--------->     "+correo);
            System.out.println("cerrar-licencia------->     "+licencia);
            System.out.println("cerrar-coincidencia--->     "+CerrarOK);
            System.out.println("cerrar-esadoOPE------->     "+estadoOpe);
            System.out.println("cerrar-Estado--------->     "+top1ticketAtencion.getnEstado());
            
            return CerrarOK;
        } catch (Exception e) {
        	e.printStackTrace();
			return 0;
		}
    }
    @Override
    public String extraerEstado(Integer nIdSimVideCola) throws Exception {
    	WebexAtencionBean objSalida = null;
    	objSalida = webexTokenDao.buscaAtencionTicket(nIdSimVideCola);
    	return objSalida.getnEstado();
    }

    @Override
    public void ActualizarCola() {
        CollectorGarbageE3();

    }
    public String obtenerEstadoOpe(String correo) {
        JsonObject opeEstado = GetAllOperadores();
        String estado = opeEstado.get(correo).toString().replace("\"", "");
        return estado;
    }



    public void CollectorGarbageE3() {
        List<VideColaBeanEnTurno> videOpeWebexBeanList = webexTokenDaoCron.HayRegistrosEnTurno();

        if (!videOpeWebexBeanList.isEmpty()) {
            Timestamp ahora = new Timestamp((System.currentTimeMillis()));
            for (int i = 0; i < videOpeWebexBeanList.size(); i++) {
                Timestamp fechaTurno = videOpeWebexBeanList.get(i).getdFecEnTurno();
                if (ahora.getTime() - fechaTurno.getTime() > 90000) {// 1 minuto y medio
                    webexTokenDaoCron.Limpiar(videOpeWebexBeanList.get(i).getnIdSimVideCola());
                }
            }
        }
    }

    public JsonObject GetAllOperadores() {
        JsonObject listaope = new JsonObject();
        // Obtengo un token de integracion válido
        String token = GetTokenIntegracion();
        // Consumo el servicio de Webex para Obtener un JSON con info de mi organizacion
        String url = "https://webexapis.com/v1/people";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + token);
        HttpEntity request = new HttpEntity(headers);
        int maxRetries = 3;
        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
        	ResponseEntity<String> response=null;
            try {
            	try {
                response = restTemplate.exchange(
                        url, HttpMethod.GET, request, String.class
                );
                }catch (Exception e) {
					response =null;
					logger.warn("Se asignó null - error 429 (CONTROLADO)");
				}
            	                               
                if (response!=null && response.getStatusCode() == HttpStatus.OK) {
                    // Convierto el response a un JsonObject
                    JsonObject jsonObj =  new Gson().fromJson(response.getBody().toString(), JsonObject.class);
                    // Solo me interesa el sub objeto items
                    JsonArray Operadores = jsonObj.getAsJsonArray("items");
                    // Armamos un objeto con los operadores y sus estados
                    for (int i=0; i < Operadores.size(); i++){
                        if (!(Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0).getAsString().equals("jlavalle@migraciones.gob.pe") || Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0).getAsString().equals("migraperuconf01@migraciones.gob.pe"))) {
                            listaope.addProperty(Operadores.getAsJsonArray().get(i).getAsJsonObject().get("emails").getAsJsonArray().get(0).getAsString(), Operadores.getAsJsonArray().get(i).getAsJsonObject().get("status").getAsString());
                        }
                    }
                    break;  // Salir del bucle de reintentos si la solicitud es exitosa
                } else if (response==null||response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                	Thread.sleep(5000);
                } else {
                    logger.warn("Error en el servicio WEBEX");
                    break;
                }
            }
            catch (HttpClientErrorException.TooManyRequests e) {
                String errorMessage = e.getMessage();
                logger.warn("Error: " + errorMessage);
            }catch (Exception e) {
            	e.printStackTrace();
                break;
                }
            }
        return listaope;
    }

    @Override
    public WebexAtencionBean updateTerminarLlamada(WebexAtencionBean webex) {
    	try {
    		String correo = webex.getsLicenciaAsignar();

            WebexAtencionBean objSalida = null;
            String estadoOpe = obtenerEstadoOpe(correo);
            System.out.println("Estado del operador"+ correo+" es "+estadoOpe);

            if(!estadoOpe.equals("call")) {
                int resultado = webexTokenDao.actualizarTerminarLlamada(correo, webex.getnIdSimVideCola());
                System.out.println("Se terminó la llamada, cod: "+resultado);
                objSalida = webexTokenDao.buscaAtencionTicket(webex.getnIdSimVideCola());
            }else {
                System.out.println("la llamada aún no ha terminado.......");
            }

            return objSalida;
		} catch (Exception e) {
			return webexTokenDao.buscaAtencionTicket(webex.getnIdSimVideCola());
		}
    }

    @Override
    public List<LicenciaBean> listLicencias() {
        return licenciaDao.listLicencias();
    }
    
    @Override
    public List<OperadoresBean> listOperadores() {
        return licenciaDao.listOperadores();
    }

    @Override
    public List<WebexSimVideColaBean> getColaFiltros(String sOpeAsignado, String fechaInicial, String fechaFinal)
            throws Exception {


        return webexTokenDao.getColaFiltros(sOpeAsignado, fechaInicial, fechaFinal);
    }

	@Override
	public LicenciaBean licenciaAsignada(Authentication authentication) {
		UserDetailsCustom user = (UserDetailsCustom) authentication.getPrincipal();
		Integer idUsuario = user.getSesionVo().getnIdOperador();
        LicenciaBean licencia = webexTokenDao.licenciaAsignada(idUsuario);
		
		return licencia;
	}
	
    @Override
    public OperadoresBean verificaNuevoOperador(String sLogin) throws Exception {
    	OperadoresBean objSalida = null;
    	objSalida = webexTokenDao.obtenerOperador(sLogin);
    	return objSalida;
    }
    
    @Override
    public OperadoresBean getOperador(String sLogin) {
    	OperadoresBean objSalida = null;
    	objSalida = licenciaDao.getOperador(sLogin);
        return objSalida;
    }
    
    @Override
    public ResponseDTO<Integer> notificarRegistrarOperador(OperadoresBean operadoresBean) {
        int generatedKey = webexTokenDao.registrarOperador(operadoresBean.getsLogin());
        return Utilitario.getInstancia().responseOK(generatedKey);
    }
    
    @Override
    public List<OperadoresBean> listaCbxOperadores() {
        return webexTokenDao.listaCbxOperadores();
    }
    
    @Override
    public ResponseDTO<LicenciaBean> updateLicencia(LicenciaBean licenciaBean) {
    	LicenciaBean objSalida = webexTokenDao.updateLicencia(licenciaBean);
        return Utilitario.getInstancia().responseOK(objSalida);
    }
    
    @Override
    public ResponseDTO<OperadoresBean> updateOperador(OperadoresBean operadoresBean) {
    	OperadoresBean objSalida = webexTokenDao.updateOperador(operadoresBean);
        return Utilitario.getInstancia().responseOK(objSalida);
    }
    
    @Override
    @Transactional
    public ResponseDTO<LicenciaBean> updateOpeLicencia(LicenciaBean licenciaBean, Integer idOpeAntiguo, Integer idOpeNuevo){
    	LicenciaBean objSalida = null;
    	objSalida = webexTokenDao.updateLicencia(licenciaBean);
    	if(idOpeAntiguo==-1) {
    		webexTokenDao.actualizarLicAsignada(idOpeNuevo);
    	}
    	if(idOpeNuevo==-1) {
    		webexTokenDao.actualizarLicAsignada(idOpeAntiguo);
    	}
    	if(idOpeAntiguo != -1 && idOpeNuevo !=-1) {
    		webexTokenDao.actualizarLicAsignada(idOpeNuevo);
    		webexTokenDao.actualizarLicAsignada(idOpeAntiguo);
    	}
    	return Utilitario.getInstancia().responseOK(objSalida);
    }    

}
