package pe.gob.migraciones.sgv.videollamadas.dao;

import pe.gob.migraciones.sgv.videollamadas.bean.OperadorBean;

public interface OperadorDao {
    OperadorBean getOperador(Integer nIdOperador);
}
