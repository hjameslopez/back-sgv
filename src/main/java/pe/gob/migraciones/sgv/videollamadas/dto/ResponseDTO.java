package pe.gob.migraciones.sgv.videollamadas.dto;
public class ResponseDTO<T> {

    private String codigo;
    private String mensaje;
    private T data;

    public ResponseDTO() {

    }
    public ResponseDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    public ResponseDTO(String codigo, String mensaje, T data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}

