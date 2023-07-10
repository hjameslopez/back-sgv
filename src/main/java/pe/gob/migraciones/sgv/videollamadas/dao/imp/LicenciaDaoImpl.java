package pe.gob.migraciones.sgv.videollamadas.dao.imp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadoresBean;
import pe.gob.migraciones.sgv.videollamadas.dao.LicenciaDao;

@Repository
public class LicenciaDaoImpl implements LicenciaDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public LicenciaBean getLicencia (String sLogin) {
        String sql = "SELECT top 1 L.* , u.snombre FROM [dbo].[SimVidLicencia] l left join  simusuario u on l.SlOGIN = u.SLOGIN where l.sLogin = ?";
        LicenciaBean licenciaBean = null;
        try {
            licenciaBean = this.jdbcTemplate.queryForObject(sql, new Object[]{sLogin}, BeanPropertyRowMapper.newInstance(LicenciaBean.class));

        } catch (EmptyResultDataAccessException e) {
        }
        return licenciaBean;
    }
    
    @Override
    public List<LicenciaBean> listLicencias() {
        //Ordenado por licencia 1-24
        String sql = "SELECT L.nIdLicencia, L.sLicencia, L.sLogin, L.sCorreo, L.sContraseña, L.dFechaHoraAud, L.bActivo, u.snombre FROM [dbo].[SimVidLicencia] l left join  simusuario u on l.SlOGIN = u.SLOGIN";

        //Ordenado alfabeticamente
        //String sql = "SELECT L.* , u.snombre FROM [dbo].[SimVidLicencia] l left join  simusuario u on l.SlOGIN = u.SLOGIN order by u.snombre ASC";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(LicenciaBean.class));
    }
    
    @Override
    public List<OperadoresBean> listOperadores() {
        
        String sql = "SELECT o.nIdOperador, o.sLogin, o.dFecCreado, o.bActivo, u.snombre FROM [dbo].[SimVidOperador] o left join  simusuario u on o.SlOGIN = u.SLOGIN";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OperadoresBean.class));
    }




}
