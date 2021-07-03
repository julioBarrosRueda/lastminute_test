package com.sales.last;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class LastminuteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastminuteApplication.class, args);
	}
	
	@Bean
    public OpenAPI customOpenAPIData() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Lastminute exercise -> BKN")
                        .description("This API provides Lastminute exercise core.") );
    }

}
