package br.com.msacademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAcademicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAcademicoApplication.class, args);
    }
}
