package pe.gob.migraciones.sgv.videollamadas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pe.gob.migraciones.sgv.videollamadas.util.Constantes;
import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadorBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadoresBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexAtencionBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexSimVideColaBean;
import pe.gob.migraciones.sgv.videollamadas.dto.ResponseDTO;
import pe.gob.migraciones.sgv.videollamadas.service.WebexService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Api(value = "Videollamada Webex")
@RequestMapping("videollamada")
//@PreAuthorize("hasAuthority('SGV_GESTION_COLA')")
public class WebexController {
    @Autowired
    private WebexService webexService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "Retorna la cola con sus estados")
    @GetMapping(value = "videollamadas-cola", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String, Object>> getColaEstados(@RequestParam(name = "bActivo") Integer bActivo) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            List<WebexSimVideColaBean> objSalida = webexService.getColaEstados(bActivo);
            if (objSalida == null) {
                salida.put("mensaje", "No hay tickets en cola");
            } else {
                salida.put("mensaje", "Listado de tickets:");
                salida.put("lista",objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No llegó el listado");
        }
        return ResponseEntity.ok(salida);
    }

    @GetMapping("/busca-atencion")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> buscaListAtencionTicket(
            @RequestParam(name = "nIdSimVideCola") Integer nIdSimVideCola) {

        Map<String, Object> salida = new HashMap<String, Object>();
        try {
            List<WebexAtencionBean> lista = webexService.buscaListAtencionTicket(nIdSimVideCola);
            if(CollectionUtils.isEmpty(lista)){
                salida.put("mensaje", "NO HAY CORRESPONDENCIA CON EL TICKET");
            }else {
                salida.put("lista", lista);
                salida.put("mensaje", "CANTIDAD DE TICKETS: " + lista.size());
            }
        } catch (Exception e) {
        	logger.error("Error -> "+e);
            salida.put("mensaje", "Error : " + e.getMessage());
        }

        return ResponseEntity.ok(salida);
    }
    @ApiOperation(value = "Valida Operador asignado")
    @GetMapping(value = "videollamadas-cola-operador", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String, Object>> validarOperador(@RequestParam(name = "nIdSimVideCola") Integer nIdSimVideCola, Authentication authentication) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            Integer objSalida = webexService.validaOperador(nIdSimVideCola, authentication);
            if (objSalida == 333) {
                salida.put("mensaje", "El ticket ha sido asignado a otro operador");
                salida.put("opeOk",objSalida);
            }else if(objSalida==0) {
            	salida.put("mensaje", "Error en la licencia");
                salida.put("opeOk",objSalida);
            }
            else {
                salida.put("mensaje", "todo OK continua con la llamada");
                salida.put("opeOk",objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No se pudo verificar");
        }
        return ResponseEntity.ok(salida);

    }

    @ApiOperation(value = "Retorna la cola con estado 2")
    @GetMapping(value = "videollamadas-cola-2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getColaEstado2(@RequestParam(name = "bActivo") Integer bActivo) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {

            List<WebexSimVideColaBean> objSalida = webexService.getColaEstado2(bActivo);
            if (objSalida == null) {
                salida.put("mensaje", "No hay tickets en cola");
            } else {
                salida.put("mensaje", "Listado de tickets:");
                salida.put("lista",objSalida);

            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No llegó el listado");
        }
        return ResponseEntity.ok(salida);
    }


    @ApiOperation(value = "Operador Asignado")
    @PostMapping(value = "asigna-operador", produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity<Map<String, Object>> updateAsignarOperador(@RequestBody WebexAtencionBean obj) throws Exception {
        Map<String, Object> salida = new HashMap<>();
            try {
                WebexAtencionBean objSalida = webexService.updateAsignarOperador(obj);
                if (objSalida == null) {
                    salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
                } else {
                    if(obj.getsOpeAsignado()==null) {
                        salida.put("mensaje", "ha sido asignado al ticket: "+obj.getsCodTicket());
                    }else {
                        salida.put("mensaje", "El ticket ha sido asignado a otro operador");
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("Error -> "+e);
                salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
            }
        return ResponseEntity.ok(salida);
    }
    
    
    @ApiOperation(value = "Terminar llamada")
    @PostMapping(value = "terminar-llamada", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateTerminarLlamada(@RequestBody WebexAtencionBean obj) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            WebexAtencionBean objSalida = webexService.updateTerminarLlamada(obj);
            if (objSalida == null) {
                salida.put("mensaje", "No se pudo terminar la llamada");
                salida.put("lista", objSalida);
            } else {
                salida.put("mensaje", "Se ha terminado la atención del ticket: "+obj.getsCodTicket());
                salida.put("lista", objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "Error al terminar la llamada");
        }
        return ResponseEntity.ok(salida);
    }
    //@PreAuthorize("hasAuthority('SGV_GESTION_COLA')")
    @GetMapping("/licencias")
    @ResponseBody
    public ResponseEntity<List<LicenciaBean>> listaLicencias() {
        List<LicenciaBean> lista = webexService.listLicencias();
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/operadores")
    @ResponseBody
    public ResponseEntity<List<OperadoresBean>> listOperadores() {
        List<OperadoresBean> lista = webexService.listOperadores();
        return ResponseEntity.ok(lista);
    }
    
    
    @GetMapping("/licencia-asignada")
    @ResponseBody
    public ResponseEntity<LicenciaBean> licenciaAsignada(Authentication authentication) {
    	LicenciaBean licencia = null;
        licencia = webexService.licenciaAsignada(authentication);
        return ResponseEntity.ok(licencia);
    }

    @ApiOperation(value = "Retorna la cola con filtros")
    @GetMapping(value = "videollamadas-cola-filtros", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getColaFiltros(	@RequestParam(name = "sOpeAsignado") String sOpeAsignado,
                                                                  @RequestParam(name = "sfechaInicial") String fechaInicial,
                                                                  @RequestParam(name = "sfechaFinal") String fechaFinal) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {

            List<WebexSimVideColaBean> objSalida = webexService.getColaFiltros(sOpeAsignado, fechaInicial, fechaFinal);
            if (objSalida == null) {
                salida.put("mensaje", "No hay tickets en cola");
            } else {
                salida.put("mensaje", "Listado de tickets:"+objSalida.size());
                salida.put("lista",objSalida);

            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No llegó el listado");
        }
        return ResponseEntity.ok(salida);
    }
    @ApiOperation(value = "Valida Cerrar")
    @GetMapping(value = "videollamadas-cerrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String, Object>> validarCerrar(@RequestParam(name = "nIdSimVideCola") Integer nIdSimVideCola, Authentication authentication) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            Integer objSalida = webexService.validaCerrar(nIdSimVideCola, authentication);
            if (objSalida == 222) {
                salida.put("mensaje", "No puedes cerrar, esperando aceptación del ciudadano...");
                salida.put("opeOk",objSalida);
            }else if(objSalida == 0) {
            	salida.put("mensaje", "ERROR con la Licencia de Webex");
                salida.put("opeOk",objSalida);
            }
            else if(objSalida == 333) {
                salida.put("mensaje", "No puede cerrar, la llamada no ha terminado...");
                salida.put("opeOk",objSalida);
            }else if(objSalida == 555) {
                salida.put("mensaje", "Seleccionar el botón \"Terminar Llamada\"...");
                salida.put("opeOk",objSalida);
            }else if(objSalida == 444) {
                salida.put("mensaje", "Para salir, seleccionar el botón \"Cerrar\"...");
                salida.put("opeOk",objSalida);
            }else {
                salida.put("mensaje", "Cerrar exitoso");
                salida.put("opeOk",objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No se pudo verificar");
        }
        return ResponseEntity.ok(salida);
    }
    @ApiOperation(value = "Extrae el estado del ticket")
    @GetMapping(value = "extraerEstado", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String, Object>> extraerEstado(@RequestParam(name = "nIdSimVideCola") Integer nIdSimVideCola) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            String objSalida = webexService.extraerEstado(nIdSimVideCola);
            if (objSalida == null) {
                salida.put("mensaje", "No se ha seleccionado Ticket");
                salida.put("estado",objSalida);
            } else {
                salida.put("mensaje", "Estado: "+objSalida);
                salida.put("estado",objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No se pudo verificar");
        }
        return ResponseEntity.ok(salida);
    }
    @ApiOperation(value = "Verifica el nuevo operador por sLogin")
    @GetMapping(value = "verificaNuevoOperador", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String, Object>> verificaNuevoOperador(@RequestParam(name = "sLogin") String sLogin) throws Exception {
        Map<String, Object> salida = new HashMap<>();
        try {
            OperadoresBean objSalida = webexService.verificaNuevoOperador(sLogin);
            if (objSalida == null) {
            	objSalida = webexService.getOperador(sLogin);
            	if(objSalida == null) {
            		salida.put("mensaje", "Ingrese un usuario válido");
            		salida.put("cod",333);
                    salida.put("operador",objSalida);
            	}
            	else {
            		salida.put("mensaje", "Verificado");
            		salida.put("cod",222);
                    salida.put("operador",objSalida);
            	}
            } else {
                salida.put("mensaje", "El operador: "+objSalida.getsNombre()+" ya se encuentra registrado");
                salida.put("cod",111);
                salida.put("operador",objSalida);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> "+e);
            salida.put("mensaje", "No se pudo verificar el operador");
            salida.put("cod", 444);
        }
        return ResponseEntity.ok(salida);
    }
    
    @ApiOperation(value = "Registra a un nuevo operador")
    @PostMapping(value = "/registrar-operador", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Integer>> registrarOperador(@RequestBody OperadoresBean operadoresBean) {
        try {
            if (operadoresBean == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(webexService.notificarRegistrarOperador(operadoresBean), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/cbx-operadores")
    @ResponseBody
    public ResponseEntity<List<OperadoresBean>> listaCbxOperadores() {
        List<OperadoresBean> lista = webexService.listaCbxOperadores();
        return ResponseEntity.ok(lista);
    }
    
    @ApiOperation(value = "Actualizar Licencia")
    @PutMapping(value = "update-licencia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<LicenciaBean>> updateLicencia(@RequestBody LicenciaBean licenciaBean) {
        ResponseDTO<LicenciaBean> salida = new ResponseDTO<>();
        try {
            ResponseDTO<LicenciaBean> objSalida = webexService.updateLicencia(licenciaBean);
            if (objSalida.getData() == null) {
                salida.setMensaje("No se actualizó");
                salida.setCodigo("333");
            } else {
                salida.setMensaje("Ha sido actualizada la licencia: " + objSalida.getData().getsLicencia());
                salida.setData(objSalida.getData());
                salida.setCodigo("222");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> " + e);
            salida.setMensaje("Error al actualizar la licencia");
            salida.setCodigo("444");
        }
        return ResponseEntity.ok(salida);
    }
    
    @ApiOperation(value = "Actualizar Operador")
    @PutMapping(value = "update-operador", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<OperadoresBean>> updateOperador(@RequestBody OperadoresBean operadoresBean) {
        ResponseDTO<OperadoresBean> salida = new ResponseDTO<>();
        try {
            ResponseDTO<OperadoresBean> objSalida = webexService.updateOperador(operadoresBean);
            if (objSalida.getData() == null) {
                salida.setMensaje("No se actualizó");
                salida.setCodigo("333");
            } else {
                salida.setMensaje("Ha sido actualizado el operador: " + objSalida.getData().getsNombre());
                salida.setData(objSalida.getData());
                salida.setCodigo("222");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -> " + e);
            salida.setMensaje("Error al actualizar operador");
            salida.setCodigo("444");
        }
        return ResponseEntity.ok(salida);
    }
    
    
    

    
    
    
    
    
    
    
}
