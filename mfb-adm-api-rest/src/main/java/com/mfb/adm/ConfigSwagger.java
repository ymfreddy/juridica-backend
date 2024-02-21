package com.mfb.adm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigSwagger {

	private static final String BEARER_AUTH = "Bearer";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mfb")).paths(PathSelectors.any())
				.build().apiInfo(apiEndPointsInfo()).securitySchemes(securitySchemes())
				.securityContexts(securityContext());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Spring Boot - Security REST API").description("Security REST SPV API")
				.contact(new Contact("MBF", "sa", "soporte@mfb.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0")
				.build();
	}

	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> lista = new ArrayList<>();
		lista.add(new ApiKey(BEARER_AUTH, "Authorization", "header"));
		return lista;
	}

	private List<SecurityContext> securityContext() {

		List<SecurityReference> listaSecurity = new ArrayList<>();
		listaSecurity.add(new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]));
		List<SecurityContext> lista = new ArrayList<>();
		lista.add(SecurityContext.builder().securityReferences(listaSecurity).forPaths(PathSelectors.any()).build());
		return lista;
	}
}