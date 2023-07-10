package pe.gob.migraciones.sgv.videollamadas.dao;

import pe.gob.migraciones.sgv.videollamadas.bean.*;

import java.util.List;

public interface WebexTokenDaoCron {
    List<WebexTokenBean> findToken(String docidentidad);
    String insToken(WebexTokenBean webexTokenBean);
    boolean archivaToken(String token);

    List<VideColaBean> LastRegEnCallConOpe(String operador);
    boolean liberarReg(Integer nIdSimVideCola);

    List<VideColaBeanEnTurno> RegistroEnTurno(String sOpeAsignado);

    boolean PonerEnTurno(String sOpeAsignado);
    List<VideColaBean> obtenerColaEspera();

    // NUEVO
    List<VideOpeWebexBean> GetAllOperadores();
    List<VideColaBeanEnTurno> HayRegistrosEnTurno();
    boolean Limpiar(Integer nIdSimVideCola);
    List<OperadorWebexBean> listarOperadores();
}
