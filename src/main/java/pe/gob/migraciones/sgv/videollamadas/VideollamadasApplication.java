package pe.gob.migraciones.sgv.videollamadas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"pe.gob.migraciones.sgv.videollamadas" })
public class VideollamadasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideollamadasApplication.class, args);
	}

}
