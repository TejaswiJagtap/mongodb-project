package com.artcode.thirtyfifty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI baseoOpenAPI() {

		  final String securitySchemeName = "bearerAuth";
		   
		    return new OpenAPI()
		        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
		        .components(
		            new Components()
		                .addSecuritySchemes(securitySchemeName,
		                    new SecurityScheme()
		                        .name(securitySchemeName)
		                        .type(SecurityScheme.Type.HTTP)
		                        .scheme("bearer")
		                        .bearerFormat("JWT")
		                )
		        )
		        .info(new Info().title("Thirty Fifty Project API").version("1.6.5").description("This Is Thirty Fifty API Documentation"));
	}
	
}
