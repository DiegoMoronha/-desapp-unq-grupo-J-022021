package ar.edu.unq.desapp.grupoJ022021.backenddesappapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BackendDesappApiApplication {

    public static Logger logger = LogManager.getLogger(BackendDesappApiApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(BackendDesappApiApplication.class, args);
	}

}
