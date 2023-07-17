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
        String sql = "SELECT L.nIdLicencia, L.sLicencia, L.sLogin, L.sCorreo, L.sContraseña, L.dFechaHoraAud, L.bActivo, u.snombre, o.nIdOperador\r\n"
        		+ "				FROM [dbo].[SimVidLicencia] l \r\n"
        		+ "				LEFT JOIN  simusuario u \r\n"
        		+ "				ON l.SlOGIN = u.SLOGIN\r\n"
        		+ "				LEFT JOIN SimVidOperador o\r\n"
        		+ "				ON l.sLogin = o.sLogin";

        //Ordenado alfabeticamente
        //String sql = "SELECT L.* , u.snombre FROM [dbo].[SimVidLicencia] l left join  simusuario u on l.SlOGIN = u.SLOGIN order by u.snombre ASC";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(LicenciaBean.class));
    }
    
    @Override
    public OperadoresBean getOperador (String sLogin) {
        String sql = "SELECT TOP 1 NULL AS 'nIdOperador', dFechaHoraAud, sLogin, 1 AS 'bActivo',  sNombre  \r\n"
        		+ "		FROM simusuario WHERE sLogin = ?  \r\n"
        		+ "		ORDER BY dFechaHoraAud DESC";
        OperadoresBean OperadorBean = null;
        try {
            OperadorBean = this.jdbcTemplate.queryForObject(sql, new Object[]{sLogin}, BeanPropertyRowMapper.newInstance(OperadoresBean.class));

        } catch (EmptyResultDataAccessException e) {
        }
        return OperadorBean;
    }
    
    @Override
    public List<OperadoresBean> listOperadores() {
        
        String sql = "SELECT o.nIdOperador, o.sLogin, o.dFecCreado, o.bActivo, u.snombre FROM [dbo].[SimVidOperador] o left join  simusuario u on o.SlOGIN = u.SLOGIN";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OperadoresBean.class));
    }




}
