package br.com.msacademico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI msAcademicoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Academico API")
                        .description("API para gerenciamento academico de escolas, cursos, matrizes e disciplinas.")
                        .version("v1"));
    }
}
