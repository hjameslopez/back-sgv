package pe.gob.migraciones.sgv.videollamadas.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadorBean;
import pe.gob.migraciones.sgv.videollamadas.dao.OperadorDao;

@Repository
public class OperadorDaoImp implements OperadorDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public OperadorBean getOperador(Integer nIdOperador) {
        String sql = "Select opr.nIdOperador, usr.sLogin,  usr.sNombre sNombreOperador, opr.bActivo, usr.sIdDependencia, usr.sDni from SimOperador opr inner join SimUsuario usr on opr.nIdOperador= usr.nIdOperador WHERE opr.nIdOperador=? and opr.bActivo=1";
        OperadorBean operadorBean = null;
        try {
            operadorBean = this.jdbcTemplate.queryForObject(sql, new Object[]{nIdOperador}, BeanPropertyRowMapper.newInstance(OperadorBean.class));
        } catch (EmptyResultDataAccessException e) {
            //logger.error("Error en getOperador");
        }
        return operadorBean;
    }
}
