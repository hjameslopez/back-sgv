package pe.gob.migraciones.sgv.videollamadas.util;

import pe.gob.migraciones.sgv.videollamadas.dto.ResponseDTO;

public class Utilitario {

    private static Utilitario instancia = null;

    private Utilitario() {}

    public static Utilitario getInstancia() {
        if ( instancia == null) {
            instancia = new Utilitario();
        }
        return instancia;
    }


    public <T> ResponseDTO<T> responseOK(){
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setCodigo(Constantes.COD.EX_200);
        response.setMensaje(Constantes.MSG.EX_200);
        response.setData(null);
        return response;
    }

    public <T> ResponseDTO<T> responseOK(T data){
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setCodigo(Constantes.COD.EX_200);
        response.setMensaje(Constantes.MSG.EX_200);
        response.setData(data);
        return response;
    }


    public <T> ResponseDTO<T> responseOK(T data, String mensaje){
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setCodigo(Constantes.COD.EX_200);
        response.setMensaje(mensaje);
        response.setData(data);
        return response;
    }

}


