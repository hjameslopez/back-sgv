package pe.gob.migraciones.sgv.videollamadas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

    @Configuration
    @PropertySource({"classpath:datasource.properties"})
    public class DataSourceConfig {
        @Value("${spring.primary.datasource.jndi-name:#{null}}")
        private String primaryJndiName;

        private JndiDataSourceLookup lookup = new JndiDataSourceLookup();

        //Configuración de Data source Primario
        @Primary
        @Bean(destroyMethod = "", name = "dsPrimary")
        @ConfigurationProperties(prefix = "spring.primary.datasource")
        public DataSource dataSourceDbPrimary() {
            DataSource dataSource;
            if (this.primaryJndiName != null && !this.primaryJndiName.equals("")) {
                dataSource = lookup.getDataSource(primaryJndiName);
            } else {
                dataSource = DataSourceBuilder.create().build();
            }
            return dataSource;
        }


        //Templates para Data Sources Primario
        @Bean(name = "jdbcTemplateDbPrimary")
        @Primary
        @Autowired
        public JdbcTemplate jdbcTemplateDbSim(@Qualifier("dsPrimary") DataSource ds){
            return new JdbcTemplate(ds);
        }

        @Bean(name = "namedParameterJdbcTemplateDbPrimary")
        @Primary
        @Autowired
        public NamedParameterJdbcTemplate namedParameterJdbcTemplateDbSim(@Qualifier("dsPrimary") DataSource ds) {
            return new NamedParameterJdbcTemplate(ds);
        }

        //Transaction manager
        @Bean(name = "txmPrimary")
        @Autowired
        @Primary
        DataSourceTransactionManager tm1(@Qualifier("dsPrimary") DataSource datasource) {
            return new DataSourceTransactionManager(datasource);
        }

    }