package pe.gob.migraciones.sgv.videollamadas.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import pe.gob.migraciones.sgv.videollamadas.bean.LicenciaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.OperadoresBean;
import pe.gob.migraciones.sgv.videollamadas.bean.SesionWebBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexAtencionBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexCorrelativoBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexListaNegraBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexMensajeInicialBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexSimVideColaBean;
import pe.gob.migraciones.sgv.videollamadas.bean.WebexTokenBean;
import pe.gob.migraciones.sgv.videollamadas.dao.WebexTokenDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WebexTokenDaoImpl implements WebexTokenDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<WebexTokenBean> findToken(String docidentidad) {

        List<WebexTokenBean> webexTokenBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT sAccToken,dFecFinAccToken,sRefToken,dFecFinRefToken FROM SimTokWebex");
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
        String sql = "INSERT INTO SimTokWebex (sCodIdentificador, sAccToken, dFecFinAccToken, sRefToken, dFecFinRefToken ) \n"
                + "VALUES (?, ?, ?, ?, ?);";

        String vReturn = null;
        try {
            this.jdbcTemplate.update(sql.toString(), new Object[] {
                    // webexTokenBean.getnIdSimTokWebex(),
                    webexTokenBean.getsCodIdentificador(), webexTokenBean.getsAccToken(),
                    webexTokenBean.getdFecFinAccToken(), webexTokenBean.getsRefToken(),
                    webexTokenBean.getdFecFinRefToken() });
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
        int rowsUpdate = jdbcTemplate.update(sql.toString(), token);
        return rowsUpdate > 0;
    }

    @Override
    public List<SesionWebBean> findUuid(String nidsesion) {

        List<SesionWebBean> sesionWebBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" Select uIdpersona FROM SimSesionWeb");
        sql.append(" WHERE nIdSesionWeb = ? ");
        params.put("nIdSesionWeb", nidsesion);
        sesionWebBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            SesionWebBean sesionWebBean = new SesionWebBean();
            sesionWebBean.setuIdPersona(rs.getString("uIdpersona"));
            return sesionWebBean;
        }, nidsesion);

        return sesionWebBeanList;
    }

    // ****New
    @Override
    public List<WebexListaNegraBean> isUserBlocked(String uid) {

        List<WebexListaNegraBean> webexListaNegraBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT TOP 1 nIdSimVideLisNeg FROM SIM.dbo.SimVideLisNeg ");
        sql.append(" WHERE sIdPersona = ? ");
        sql.append(" AND nEstado = 1 ");
        sql.append(" AND bActivo = 1 ");
        sql.append(" ORDER BY nIdSimVideCola DESC ");
        params.put("sIdPersona", uid);
        webexListaNegraBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexListaNegraBean webexListaNegraBean = new WebexListaNegraBean();
            webexListaNegraBean.setnIdSimVideLisNeg(rs.getInt("nIdSimVideLisNeg"));
            return webexListaNegraBean;
        }, uid);

        return webexListaNegraBeanList;
    }

    @Override
    public List<WebexMensajeInicialBean> MensajeInicial(String sTipo) {

        List<WebexMensajeInicialBean> webexMensajeInicialBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT sTipo, sTitulo, sTexto FROM SIM.dbo.SimVideMenIni ");
        sql.append(" WHERE sTipo = ? ");
        sql.append(" AND bActivo = 1 ");
        params.put("sTipo", sTipo);
        webexMensajeInicialBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexMensajeInicialBean webexMensajeInicialBean = new WebexMensajeInicialBean();
            webexMensajeInicialBean.setsTipo(rs.getString("sTipo"));
            webexMensajeInicialBean.setsTitulo(rs.getString("sTitulo"));
            webexMensajeInicialBean.setsTexto(rs.getString("sTexto"));
            return webexMensajeInicialBean;
        }, sTipo);

        return webexMensajeInicialBeanList;
    }

    @Override
    public List<WebexSimVideColaBean> isUserInCall(String uid) {

        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT TOP 1 nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE sIdPersona = ? ");
        sql.append(" AND nEstado = 4 ");
        sql.append(" AND bActivo = 1 ");
        params.put("sIdPersona", uid);
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return webexSimVideColaBean;
        }, uid);

        return webexSimVideColaBeanList;
    }
/*
    @Override
    public void encolar(WebexSimVideColaBean webexSimVideColaBean) {
        String sql = "INSERT INTO SimVideCola (sIdPersona, sNombres, sTipTicket, sCodTicket, nEstado, nIdSesion) \n"
                + "VALUES (:sIdPersona, :sNombres, :sTipTicket, :sCodTicket, :nEstado, :nIdSesion);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(webexSimVideColaBean), keyHolder,
                new String[] { "nIdSimVideCola" });
        webexSimVideColaBean.setnIdSimVideCola(keyHolder.getKey().intValue());
    }
*/
    @Override
    public List<WebexCorrelativoBean> obtenerCorrelativo(String sTipTicket) {

        List<WebexCorrelativoBean> webexCorrelativoBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(
                " SELECT nIdSimVideGenTic, sTipTicket, sFormato, nCorrelativo FROM SimVideGenTic WHERE sTipTicket = ? AND bActivo = 1 ");
        params.put("sTipTicket", sTipTicket);
        webexCorrelativoBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexCorrelativoBean webexCorrelativoBean = new WebexCorrelativoBean();
            webexCorrelativoBean.setnIdSimVideGenTic(rs.getInt("nIdSimVideGenTic"));
            webexCorrelativoBean.setsTipTicket(rs.getString("sTipTicket"));
            webexCorrelativoBean.setsFormato(rs.getString("sFormato"));
            webexCorrelativoBean.setnCorrelativo(rs.getInt("nCorrelativo"));
            return webexCorrelativoBean;
        }, sTipTicket);

        return webexCorrelativoBeanList;

    }

    @Override
    public boolean updateTablaTickets(Integer nCorrelativo, Integer nIdSimVideGenTic) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SIM.dbo.SimVideGenTic ");
        sql.append(" SET nCorrelativo = ?");
        sql.append(" WHERE nIdSimVideGenTic = ? ");
        sql.append(" AND bActivo = 1 ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(), ps -> {
            ps.setInt(1, nCorrelativo);
            ps.setInt(2, nIdSimVideGenTic);
        });

        return rowsUpdate > 0;
    }

    @Override
    public String activarTabColaCall() {
        String retorno = null;
        String sNombre = "VIDE_GETCOLAENATENCION";
        try {
            retorno = jdbcTemplate.queryForObject("SELECT sValor FROM SIM.dbo.SimParametro WHERE sNombre = ? ",
                    String.class, sNombre);
        } catch (EmptyResultDataAccessException e) {
        }
        return retorno;
    }

    public List<WebexSimVideColaBean> obtenerColaCall() {
        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top 1000 sOpeAsignado, sCodTicket FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nEstado = 4 ");
        sql.append(" AND bActivo = 1 ");
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setsOpeAsignado(rs.getString("sOpeAsignado"));
            webexSimVideColaBean.setsCodTicket(rs.getString("sCodTicket"));
            return webexSimVideColaBean;
        });

        return webexSimVideColaBeanList;
    }

    public List<WebexSimVideColaBean> obtenerColaEspera() {
        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top 1000 nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nEstado = 2 ");
        sql.append(" AND bActivo = 1 ");
        sql.append(" ORDER BY nIdSimVideCola ASC ");
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return webexSimVideColaBean;
        });

        return webexSimVideColaBeanList;
    }

    public List<WebexSimVideColaBean> isRegistroInTurno(Integer nIdSimVideCola) {
        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top 1000 nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        sql.append(" AND nEstado = 3 ");
        sql.append(" AND bActivo = 1 ");
        params.put("nIdSimVideCola", nIdSimVideCola);
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return webexSimVideColaBean;
        }, nIdSimVideCola);

        return webexSimVideColaBeanList;
    }

    public List<WebexSimVideColaBean> hayRegistroInTurno() {
        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top 1000 nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nEstado = 3 ");
        sql.append(" AND bActivo = 1 ");
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return webexSimVideColaBean;
        });

        return webexSimVideColaBeanList;
    }

    @Override
    public List<WebexSimVideColaBean> isUserInCola(String uid) {

        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT TOP 1 nIdSimVideCola, sCodTicket FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE sIdPersona = ? ");
        sql.append(" AND nEstado = 2 ");
        sql.append(" AND bActivo = 1 ");
        params.put("sIdPersona", uid);
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            webexSimVideColaBean.setsCodTicket(rs.getString("sCodTicket"));
            return webexSimVideColaBean;
        }, uid);

        return webexSimVideColaBeanList;
    }

    @Override
    public List<WebexSimVideColaBean> isUserInTurno(String uid) {

        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top 1000 nIdSimVideCola FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE sIdPersona = ? ");
        sql.append(" AND nEstado = 3 ");
        sql.append(" AND bActivo = 1 ");
        params.put("sIdPersona", uid);
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setnIdSimVideCola(rs.getInt("nIdSimVideCola"));
            return webexSimVideColaBean;
        }, uid);

        return webexSimVideColaBeanList;
    }

    public List<WebexSimVideColaBean> obtenerOpeAsignado(Integer nIdSimVideCola) {
        List<WebexSimVideColaBean> webexSimVideColaBeanList;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT top1 sOpeAsignado FROM SIM.dbo.SimVideCola ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        params.put("nIdSimVideCola", nIdSimVideCola);
        webexSimVideColaBeanList = jdbcTemplate.query(sql.toString(), (rs, i) -> {
            WebexSimVideColaBean webexSimVideColaBean = new WebexSimVideColaBean();
            webexSimVideColaBean.setsOpeAsignado(rs.getString("sOpeAsignado"));
            return webexSimVideColaBean;
        }, nIdSimVideCola);

        return webexSimVideColaBeanList;
    }

    @Override
    public boolean updateLlamar(Integer nIdSimVideCola) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SIM.dbo.SimVideCola ");
        sql.append(" SET nEstado = 4, dFecLlamada = GETDATE() ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(), ps -> {
            ps.setInt(1, nIdSimVideCola);
        });

        return rowsUpdate > 0;
    }

    @Override
    public boolean updateCancelarTurno(Integer nIdSimVideCola) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE SIM.dbo.SimVideCola ");
        sql.append(" SET nEstado = 7, dFecLiberado = GETDATE() ");
        sql.append(" WHERE nIdSimVideCola = ? ");
        sql.append(" AND bActivo = 1 ");
        int rowsUpdate = jdbcTemplate.update(sql.toString(), ps -> {
            ps.setInt(1, nIdSimVideCola);
        });

        return rowsUpdate > 0;
    }


    @Override
    public List<WebexSimVideColaBean> getColaEstados(Integer bActivo) {
        List<WebexSimVideColaBean> lista;
        String sql = "Select top 2000 c.nIdSimVideCola, c.sNombres, c.sCodTicket, c.sOpeAsignado, c.nEstado, c.nIdSesion, c.dFecCreado, p.dFechaNacimiento as nEdad \r\n"
        		+ "		from SIM.dbo.SimVideCola c \r\n"
        		+ "		inner join sim.dbo.simpersona p on c.sIdPersona = p.uIdPersona \r\n"
        		+ "		WHERE CONVERT(date, c.dFecCreado) = CONVERT(date, GETDATE()) \r\n"
        		+ "		and c.bActivo = ?  \r\n"
        		+ "		and c.nEstado <> '2' \r\n"
        		+ "		and p.uIdPersona <> '00000000-0000-0000-0000-000000000000' \r\n"
        		+ "		ORDER BY c.nIdSimVideCola DESC\r\n";
        lista = jdbcTemplate.query(sql, new Object[] { bActivo},
                BeanPropertyRowMapper.newInstance(WebexSimVideColaBean.class));
        return lista;
    }
    /*NO ESTA EN USO
    @Override
    public int actualizarAsignarOperador(String operador, String ticket) {
        String updateSql = "UPDATE SIM.dbo.SimVideCola SET nEstado = 3, dFecEnTurno = GETDATE(),sOpeAsignado = ? WHERE sCodTicket = ? AND bActivo = 1";
        Object[] params = { operador, ticket };
        int[] types = { Types.VARCHAR, Types.VARCHAR};
        return jdbcTemplate.update(updateSql,params, types);
    }
*/
    @Override
    public WebexAtencionBean buscaAtencionTicket(Integer nIdSimVideCola) {
        WebexAtencionBean salida = null;
        String sql = "Select nIdSimVideCola, sIdPersona, sNombres, sTipTicket, sCodTicket, dFecCita, sOpeAsignado, nEstado, dFecCreado, dFecEnTurno, dFecLiberado, dFecLlamada, dFecHoraAud, nIdSesion, bActivo from simvidecola where nIdSimVideCola = ? ";
        try {
            salida = this.jdbcTemplate.queryForObject(sql, new Object[]{nIdSimVideCola}, BeanPropertyRowMapper.newInstance(WebexAtencionBean.class));
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
        return salida;
    }
    @Override
    public List<WebexAtencionBean> buscaListAtencionTicket(Integer nIdSimVideCola) {
        List<WebexAtencionBean> lista;
        String sql = "Select nIdSimVideCola, sIdPersona, sNombres, sTipTicket, sCodTicket, dFecCita, sOpeAsignado, nEstado, dFecCreado, dFecEnTurno, dFecLiberado, dFecLlamada, dFecHoraAud, nIdSesion, bActivo "
        		+ "from simvidecola where nIdSimVideCola = ? ";

        lista = jdbcTemplate.query(sql, new Object[]{nIdSimVideCola}, BeanPropertyRowMapper.newInstance(WebexAtencionBean.class));

        return lista;
    }

    @Override
    public int actualizarTerminarLlamada(String operador, Integer nIdSimVideCola) {
        String updateSql = "UPDATE SIM.dbo.SimVideCola SET nEstado = 5, dFecEnTurno = GETDATE(),sOpeAsignado = ? WHERE nIdSimVideCola = ? AND bActivo = 1";
        Object[] params = { operador, nIdSimVideCola };
        int[] types = { Types.VARCHAR, Types.VARCHAR};
        return jdbcTemplate.update(updateSql,params, types);
    }


    @Override
    public List<WebexSimVideColaBean> getColaEstado2(Integer bActivo) {
        List<WebexSimVideColaBean> lista;
        String sql = "Select top 500 c.nIdSimVideCola, c.sNombres, c.sCodTicket, c.sOpeAsignado, c.nEstado, c.nIdSesion, c.dFecCreado, p.dFechaNacimiento as nEdad\r\n"
        		+ "from SIM.dbo.SimVideCola c inner join sim.dbo.simpersona p on c.sIdPersona = p.uIdPersona\r\n"
        		+ "WHERE CONVERT(date, c.dFecCreado) = CONVERT(date, GETDATE()) \r\n"
        		+ "and c.bActivo = ?\r\n"
        		+ "and c.nEstado='2'\r\n"
        		+ "and p.uIdPersona <> '00000000-0000-0000-0000-000000000000'\r\n"
        		+ "ORDER BY c.nIdSimVideCola ASC";
        lista = jdbcTemplate.query(sql, new Object[] { bActivo },
                BeanPropertyRowMapper.newInstance(WebexSimVideColaBean.class));
        return lista;
    }

    @Override
    public WebexSimVideColaBean validaOperador(Integer nIdSimVideCola) {
        String sql = "Select nIdSimVideCola, sIdPersona, sNombres, sTipTicket, sCodTicket, dFecCita, sOpeAsignado, nEstado, dFecCreado, dFecEnTurno, dFecLiberado, dFecLlamada, dFecHoraAud, nIdSesion, bActivo "
        		+ "from sim.dbo.simvidecola where nIdSimVideCola = ?";
        WebexSimVideColaBean top1ticketAtencion = null;
        try {
            top1ticketAtencion = this.jdbcTemplate.queryForObject(sql, new Object[]{nIdSimVideCola}, BeanPropertyRowMapper.newInstance(WebexSimVideColaBean.class));
        } catch (EmptyResultDataAccessException e) {
            
        }
        return top1ticketAtencion;
    }

    @Override
    public List<WebexSimVideColaBean> getColaFiltros(String sOpeAsignado, String fechaInicial, String fechaFinal) {
        List<WebexSimVideColaBean> lista;
        String sql = "Select top 1000 c.nIdSimVideCola, c.sNombres, c.sCodTicket, c.sOpeAsignado, c.nEstado, c.nIdSesion, c.dFecCreado, p.dFechaNacimiento as nEdad "
        		+ "from SIM.dbo.SimVideCola c "
        		+ "inner join sim.dbo.simpersona p "
        		+ "on c.sIdPersona = p.uIdPersona "
        		+ "where c.bActivo = '1' and p.uIdPersona <> '00000000-0000-0000-0000-000000000000' "
        		+ "and c.sOpeAsignado = ? "
        		+ "and CONVERT(VARCHAR(10), c.dFecCreado, 23) >= ? "
        		+ "and CONVERT(VARCHAR(10), c.dFecCreado, 23) <= ? "
        		+ "ORDER BY c.nIdSimVideCola DESC";
        lista = jdbcTemplate.query(sql, new Object[] { sOpeAsignado, fechaInicial, fechaFinal },
                BeanPropertyRowMapper.newInstance(WebexSimVideColaBean.class));

        return lista;
    }
/*    
    @Override
    public String PrimerSegundoDelDia() {
        String sql  = "SELECT DATEADD(day, DATEDIFF(day, 0, GETDATE()), 0)";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
    
    @Override
    public String UltimoSegundoDelDia() {
        String sql  = "SELECT DATEADD(second, -1, DATEADD(day, DATEDIFF(day, 0, GETDATE()) + 1, 0))";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
  */  
    @Override
    public WebexAtencionBean asignaOperador(String sCorreo, Integer nIdSimVideCola) throws SQLException {

          SimpleJdbcCall proc = new SimpleJdbcCall(jdbcTemplate)
                       .withSchemaName("dbo")
                        .withProcedureName("usp_Sim_Sgv_AsignarOperador")
                       .declareParameters(
                                    new SqlParameter("sCorreo", Types.CHAR),
                       				new SqlParameter("nIdSimVideCola", Types.CHAR))
                       .returningResultSet("result", BeanPropertyRowMapper.newInstance(WebexAtencionBean.class));

          SqlParameterSource in = new MapSqlParameterSource()
                       .addValue("sCorreo", sCorreo)
                       .addValue("nIdSimVideCola", nIdSimVideCola);

          Map<String, Object> mapOut = proc.execute(in);
          Object data = mapOut.get("result");
          if (data == null)
                 return null;

          List<WebexAtencionBean> lista = (List<WebexAtencionBean>)mapOut.get("result");
          return  lista.size()>0 ? lista.get(0) : null;
    }
    
    @Override
    public String obtenerLicencia(Integer nIdOperador) {

          SimpleJdbcCall proc = new SimpleJdbcCall(jdbcTemplate)
                       .withSchemaName("dbo")
                        .withProcedureName("usp_Sim_Sgv_ObtenerLicencia")
                       .declareParameters(
                                    new SqlParameter("nIdOperador", Types.INTEGER),
                                    new SqlOutParameter("@sCorreo", Types.VARCHAR));                       

          SqlParameterSource in = new MapSqlParameterSource()
                       .addValue("nIdOperador", nIdOperador);

          Map<String, Object> mapOut = proc.execute(in);
          String correo = (String) mapOut.get("@sCorreo");
          return correo;

    }

	@Override
	public LicenciaBean licenciaAsignada(Integer idUsuario) {
		LicenciaBean salida = null;
        String sql = "SELECT l.nIdLicencia, l.sLicencia, l.sLogin, l.sCorreo, l.sContraseña, l.dFechaHoraAud, l.bActivo \r\n"
        		+ "			FROM [dbo].[SimVidLicencia] l \r\n"
        		+ "			left join  simusuario usr \r\n"
        		+ "			on l.SlOGIN = usr.SLOGIN\r\n"
        		+ "			inner join SimOperador opr \r\n"
        		+ "			on opr.nIdOperador = usr.nIdOperador\r\n"
        		+ "			WHERE opr.nIdOperador = ?\r\n"
        		+ "			and opr.bActivo='1'";
        try {
            salida = this.jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, BeanPropertyRowMapper.newInstance(LicenciaBean.class));
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
        return salida;
	}
	
	@Override
	public OperadoresBean obtenerOperador(String sLogin) {
		OperadoresBean salida = null;
        String sql = "SELECT top 1 o.nIdOperador, o.sLogin, o.dFecCreado, o.bActivo, u.dFechaHoraAud, u.sNombre\r\n"
        		+ "	from SimVidOperador o RIGHT JOIN SimUsuario u\r\n"
        		+ "	ON o.sLogin = u.sLogin\r\n"
        		+ "	WHERE bActivo='1'AND o.sLogin = ?\r\n"
        		+ "	ORDER BY o.dFecCreado DESC";
        try {
            salida = this.jdbcTemplate.queryForObject(sql, new Object[]{sLogin}, BeanPropertyRowMapper.newInstance(OperadoresBean.class));
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
        return salida;
	}
	
	
	@Override
	public Integer registrarOperador(String sLogin) {
	    String query = "INSERT INTO SimVidOperador (sLogin, bLicAsignada) VALUES (?, 0)";

	    // Crear un GeneratedKeyHolder para almacenar la clave generada
	    KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(con -> {
	        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, sLogin);
	        return ps;
	    }, keyHolder);

	    // Obtener la clave generada
	    int generatedKey = keyHolder.getKey().intValue();

	    return generatedKey;
	}
	
    @Override
    public List<OperadoresBean> listaCbxOperadores() {
        String sql = "SELECT o.nIdOperador, o.sLogin, o.dFecCreado, o.bActivo, o.bLicAsignada, u.dFechaHoraAud, u.sNombre\r\n"
        		+ "        		from SimVidOperador o LEFT JOIN SimUsuario u\r\n"
        		+ "        		ON o.sLogin = u.sLogin\r\n"
        		+ "        		WHERE o.bLicAsignada = 0";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OperadoresBean.class));
    }
    
    @Override
    public LicenciaBean updateLicencia(LicenciaBean licenciaBean) {
        LicenciaBean salida = null;
        String sql = "UPDATE SimVidLicencia\r\n"
                + "SET sLogin = ?,\r\n"
                + "    sContraseña = ?\r\n"
                + "WHERE nIdLicencia = ?";
        try {
            // Ejecuta la consulta de actualización
            int filasActualizadas = this.jdbcTemplate.update(sql, licenciaBean.getsLogin(), licenciaBean.getsContraseña(), licenciaBean.getnIdLicencia());

            // Verifica si se actualizó al menos una fila
            if (filasActualizadas > 0) {
                // Consulta el objeto actualizado
                String consultaObjeto = "SELECT L.nIdLicencia, L.sLicencia, L.sLogin, L.sCorreo, L.sContraseña, L.dFechaHoraAud, L.bActivo, u.snombre \r\n"
                		+ "        		FROM [dbo].[SimVidLicencia] l \r\n"
                		+ "        		left join  simusuario u \r\n"
                		+ "        		on l.SlOGIN = u.SLOGIN\r\n"
                		+ "        		WHERE WHERE l.nIdLicencia = ?";
                salida = this.jdbcTemplate.queryForObject(consultaObjeto, new Object[]{licenciaBean.getnIdLicencia()}, BeanPropertyRowMapper.newInstance(LicenciaBean.class));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return salida;
    }
    
    @Override
    public OperadoresBean updateOperador(OperadoresBean operadoresBean) {
    	OperadoresBean salida = null;
        String sql = "UPDATE SimVidOperador\r\n"
        		+ "				SET bActivo = ?,\r\n"
        		+ "    			bLicAsignada = ?\r\n"
        		+ "				WHERE nIdOperador = ?";
        try {
            // Ejecuta la consulta de actualización
            int filasActualizadas = this.jdbcTemplate.update(sql, operadoresBean.getbActivo(), operadoresBean.getbLicAsignada(), operadoresBean.getnIdOperador());

            // Verifica si se actualizó al menos una fila
            if (filasActualizadas > 0) {
                // Consulta el objeto actualizado
                String consultaObjeto = "SELECT o.nIdOperador, o.sLogin, o.dFecCreado, o.bActivo, u.snombre \r\n"
                		+ "        		FROM [dbo].[SimVidOperador] o \r\n"
                		+ "        		LEFT JOIN  simusuario u \r\n"
                		+ "        		ON o.SlOGIN = u.SLOGIN\r\n"
                		+ "        		WHERE WHERE o.nIdOperador = ?";
                salida = this.jdbcTemplate.queryForObject(consultaObjeto, new Object[]{operadoresBean.getnIdOperador()}, BeanPropertyRowMapper.newInstance(OperadoresBean.class));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return salida;
    }



    
    


}
