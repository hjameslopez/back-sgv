package pe.gob.migraciones.sgv.videollamadas.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.migraciones.sgv.videollamadas.bean.*;
import pe.gob.migraciones.sgv.videollamadas.dao.WebexTokenDaoCron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WebexTokenDaoCronImpl implements WebexTokenDaoCron {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<WebexTokenBean> findToken(String docidentidad) {

        List<WebexTokenBean> webexTokenBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT * FROM SimTokWebex");
        sql.append(" WHERE sCodIdentificador = ? ");
        sql.append(" AND bActivo = 1 ");
        params.put("sCodIdentificador", docidentidad);
        webexTokenBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexTokenBean webexTokenBean = new WebexTokenBean();
            webexTokenBean.setsAccToken(rs.getString("sAccToken"));
            webexTokenBean.setdFecFinAccToken(rs.getDate("dFecFinAccToken"));
            webexTokenBean.setsRefToken(rs.getString("sRefToken"));
            webexTokenBean.setdFecFinRefToken(rs.getDate("dFecFinRefToken"));
            return webexTokenBean;
        }, docidentidad);

        return webexTokenBeanList;
    }

    @Override
    public String insToken(WebexTokenBean webexTokenBean) {
        String sql = "INSERT INTO SimTokWebex (sCodIdentificador, sAccToken, dFecFinAccToken, sRefToken, dFecFinRefToken ) \n" +
                "VALUES (?, ?, ?, ?, ?);";

        String vReturn = null;
        try {
            this.jdbcTemplate.update(sql.toString(), new Object[]{
                    //webexTokenBean.getnIdSimTokWebex(),
                    webexTokenBean.getsCodIdentificador(),
                    webexTokenBean.getsAccToken(),
                    webexTokenBean.getdFecFinAccToken(),
                    webexTokenBean.getsRefToken(),
                    webexTokenBean.getdFecFinRefToken()
            });
            vReturn = "OK";
        } catch (DuplicateKeyException e) {
            System.out.println("WARN-> Llave duplicada en SimWebexToken para " + webexTokenBean.getnIdSimTokWebex());
        }
        return vReturn;
    }

    @Override
    public boolean archivaToken(String token) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SimTokWebex ");
        sql.append(" SET bActivo = 0");
        sql.append(" WHERE sAccToken = ? ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(),token);
        return rowsUpdate > 0;
    }

    @Override
    public boolean liberarReg(Integer nIdSimVideCola) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SimVideCola ");
        sql.append(" SET nEstado = 5, dFecLiberado = GETDATE() ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(), nIdSimVideCola);

        return rowsUpdate > 0;
    }

    @Override
    public List<VideColaBean> LastRegEnCallConOpe(String operador) {

        List<VideColaBean> videColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT nIdSimVideCola FROM SimVideCola");
        sql.append(" WHERE sOpeAsignado = ? ");
        sql.append(" AND nEstado = 4 ");
        params.put("sOpeAsignado", operador);
        videColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            VideColaBean videColaBean = new VideColaBean();
            videColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return videColaBean;
        }, operador);

        return videColaBeanList;
    }

    @Override
    public List<VideColaBeanEnTurno> RegistroEnTurno(String sOpeAsignado) {

        List<VideColaBeanEnTurno> videColaBeanListEnTurno;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT TOP 1 nIdSimVideCola FROM SimVideCola ");
        sql.append(" WHERE nEstado = 3 ");
        sql.append(" AND sOpeAsignado = ? ");
        sql.append(" AND bActivo = 1 ");
        sql.append(" ORDER BY dFecCreado ASC");
        params.put("sOpeAsignado", sOpeAsignado);
        videColaBeanListEnTurno = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            VideColaBeanEnTurno videColaBeanEnTurno = new VideColaBeanEnTurno();
            videColaBeanEnTurno.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return videColaBeanEnTurno;
        }, sOpeAsignado);

        return videColaBeanListEnTurno;
    }

    @Override
    public boolean PonerEnTurno(String sOpeAsignado) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SimVideCola ");
        sql.append(" SET sOpeAsignado = ?, nEstado = 3, dFecEnTurno = GETDATE() ");
        sql.append(" WHERE nIdSimVideCola = ");
        sql.append(" (SELECT TOP 1 nIdSimVideCola FROM SimVideCola WHERE sTipTicket = 'N' AND nEstado = 2 ORDER BY dFecCreado ASC) ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(),sOpeAsignado);

        return rowsUpdate > 0;
    }

    public List<VideColaBean> obtenerColaEspera(){
        List<VideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nEstado = 2 ");
        sql.append(" AND sTipTicket = 'N' ");
        sql.append(" AND bActivo = 1 ");
        sql.append(" ORDER BY nIdSimVideCola ASC ");
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            VideColaBean videColaBean = new VideColaBean();
            videColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return videColaBean;
        });

        return webexSimVideColaBeanList;
    }

    /**
     * 2020-11-12 :: NUEVO
     */
    public List<VideOpeWebexBean> GetAllOperadores(){
        List<VideOpeWebexBean> videOpeWebexBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT sOperador, sEstado FROM SIM.dbo.SimVideOpeWebex ");
        videOpeWebexBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            VideOpeWebexBean videOpeWebexBean = new VideOpeWebexBean();
            videOpeWebexBean.setsOperador(rs.getString("sOperador"));
            videOpeWebexBean.setsEstado(rs.getString("sEstado"));
            return videOpeWebexBean;
        });
        return videOpeWebexBeanList;
    }

    @Override
    public List<VideColaBeanEnTurno> HayRegistrosEnTurno() {

        List<VideColaBeanEnTurno> videColaBeanListEnTurno;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT nIdSimVideCola, dFecEnTurno FROM SimVideCola ");
        sql.append(" WHERE nEstado = 3 ");
        sql.append(" AND bActivo = 1 ");
        videColaBeanListEnTurno = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            VideColaBeanEnTurno videColaBeanEnTurno = new VideColaBeanEnTurno();
            videColaBeanEnTurno.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            videColaBeanEnTurno.setdFecEnTurno(rs.getTimestamp("dFecEnTurno"));
            return videColaBeanEnTurno;
        });

        return videColaBeanListEnTurno;
    }

    @Override
    public boolean Limpiar(Integer nIdSimVideCola) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SIM.dbo.SimVideCola ");
        sql.append(" SET nEstado = 8, dFecLiberado = GETDATE() ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        sql.append(" AND bActivo = 1 ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(), ps -> {
            ps.setInt(1, nIdSimVideCola);
        });

        return rowsUpdate > 0;
    }

    @Override
    public List<OperadorWebexBean> listarOperadores(){
        List<OperadorWebexBean> operadorWebexBeannList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT nIdSimVideOpeWebex ");
        sql.append("        ,sOperador ");
        sql.append("        ,sEstado ");
        sql.append(" FROM SIM.dbo.SimVideOpeWebex ");
        sql.append(" WHERE bActivo = 1 ");
        operadorWebexBeannList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            OperadorWebexBean operadorWebexBean = new OperadorWebexBean();
            operadorWebexBean.setnIdOperadorWebex(rs.getInt("nIdSimVideOpeWebex"));
            operadorWebexBean.setsOperador(rs.getString("sOperador"));
            operadorWebexBean.setsEstado(rs.getString("sEstado"));
            return operadorWebexBean;
        });

        return operadorWebexBeannList;
    }
}
