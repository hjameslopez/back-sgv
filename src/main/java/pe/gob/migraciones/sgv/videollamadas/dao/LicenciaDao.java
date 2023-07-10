package pe.gob.migraciones.sgv.videollamadas.dao;

import java.util.List;

import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadoresBean;

public interface LicenciaDao {
    LicenciaBean getLicencia (String sLogin);
    public List<LicenciaBean> listLicencias();
    public List<OperadoresBean> listOperadores();
    public OperadoresBean getOperador (String sLogin);

}
