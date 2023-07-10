package pe.gob.migraciones.sgv.videollamadas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityConstants securityConstants;
    @Lazy
    @Autowired
    private TokenJwtClientCustom tokenJwtClientCustom;
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /*
         * 1. Se desactiva el uso de cookies
         * 2. Se activa la configuración CORS con los valores por defecto
         * 3. Se desactiva el filtro CSRF
         * 4. Se indica que el login no requiere autenticación
         * 5. Se indica que el resto de URLs esten securizadas
         */
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, securityConstants.getLoginUrl()).permitAll()
                .antMatchers(HttpMethod.GET, "/", "/auth/**", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()//
                .antMatchers("/rq/*").permitAll()//
                //.antMatchers("/grupos").access("hasRole('ASIGNARGRUPOS') or hasAuthority('OFICIORQ')")
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        //"/contratos/*",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security")
                .permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),securityConstants.getHeaderAuthorizationKey(), securityConstants.getTokenBearerPreix(),tokenJwtClientCustom));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration =new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","OPTIONS","DELETE"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
