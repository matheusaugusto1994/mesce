package br.com.diocesesjc.mesce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MesceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesceApplication.class, args);
	}

}
