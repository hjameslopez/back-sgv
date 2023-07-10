package pe.gob.migraciones.sgv.videollamadas.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@PropertySource({"classpath:application.properties"})
public class TokenJwtClientCustom {
    @Value("${auth.jwt.resource.publicKey}")
    private String sResourcePublicKey;
    //@Value("${auth.jwtTokenExpirationTime}")
    //private String sJwtTokenExpirationTime;

    private static final String IP_ADDRESS_REGEX = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
    private static final String PRIVATE_IP_ADDRESS_REGEX = "(^127\\.0\\.0\\.1)|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^192\\.168\\.)";
    private static Pattern IP_ADDRESS_PATTERN = null;
    private static Pattern PRIVATE_IP_ADDRESS_PATTERN = null;
    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";
    private static final String HEADER_X_IP_CLIENT = "X-IP-CLIENT";

    private Key publicKey;

    @PostConstruct
    public void inicializar() {
        publicKey = KeysLoader.getPublicKey(TokenJwtClientCustom.class, this.sResourcePublicKey);
    }

    public UserDetailsCustom getUserDetailsFromToken(String authToken, HttpServletRequest request) {
        UserDetailsCustom userDetailsCustom = null;
        if (authToken != null) {
            Claims claims = getAllClaimsFromToken(authToken);
            if (claims != null) {
                //Validación de Expiración
                Date fechaExpiracion = claims.getExpiration();
                if (new Date().compareTo(fechaExpiracion) >= 0) {
                    return null;
                }
                userDetailsCustom = new UserDetailsCustom();
                Object objSesion = claims.get("sesion", Object.class);
                Object objControlMigratorioVo = claims.get("controlMigratorio", Object.class);
                ObjectMapper mapper = new ObjectMapper();

                if (objSesion != null) {
                    SesionVo sesionVo = mapper.convertValue(objSesion, SesionVo.class);
                    userDetailsCustom.setSesionVo(sesionVo);
                }
                if (objControlMigratorioVo != null) {
                    ControlMigratorioVo controlMigratorioVo = mapper.convertValue(objControlMigratorioVo, ControlMigratorioVo.class);
                    userDetailsCustom.setControlMigratorioVo(controlMigratorioVo);
                }
                //
                List<GrantedAuthorityCustom> rolesName = (ArrayList<GrantedAuthorityCustom>) claims.get("lRoles", List.class);
                userDetailsCustom.setUsername(claims.get(Claims.SUBJECT, String.class));
                userDetailsCustom.setAuthorities(rolesName);
            }
        }
        return userDetailsCustom;
    }

    protected Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getIpClient(HttpServletRequest request) {
        String ipAddress = findNonPrivateIpAddress(request.getHeader(HEADER_X_FORWARDED_FOR));
        if (ipAddress == null) {
            ipAddress = request.getHeader(HEADER_X_IP_CLIENT);
        }
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private static String findNonPrivateIpAddress(String s) {
        if (IP_ADDRESS_PATTERN == null) {
            IP_ADDRESS_PATTERN = Pattern.compile(IP_ADDRESS_REGEX);
            PRIVATE_IP_ADDRESS_PATTERN = Pattern.compile(PRIVATE_IP_ADDRESS_REGEX);
        }
        try {
            Matcher matcher = IP_ADDRESS_PATTERN.matcher(s);
            while (matcher.find()) {
                if (!PRIVATE_IP_ADDRESS_PATTERN.matcher(matcher.group(0)).find())
                    return matcher.group(0);
                matcher.region(matcher.end(), s.length());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public String getHostnameFromRequest(HttpServletRequest request) {
        String addr = getAddressFromRequest(request);
        try {
            return Inet4Address.getByName(addr).getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    public InetAddress getInet4AddressFromRequest(HttpServletRequest request) throws UnknownHostException {
        return Inet4Address.getByName(getAddressFromRequest(request));
    }

    public String getAddressFromRequest(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && (forwardedFor = findNonPrivateIpAddress(forwardedFor)) != null)
            return forwardedFor;
        return request.getRemoteAddr();
    }
    public String generateTokenWebexGuest(String sub, String name, String iss, String secret) {

        return Jwts.builder()
                .setSubject(sub)
                .setIssuer(iss)
                .signWith(SignatureAlgorithm.HS256, secret)
                //.setClaims(getClaims())
                .claim("name", name)
                .setExpiration(getTimeTokenExpire())
                .compact();
    }
    
    
    
    private Date getTimeTokenExpire() {
        return new Date(System.currentTimeMillis() + Long.parseLong("300000"));
    }
}
