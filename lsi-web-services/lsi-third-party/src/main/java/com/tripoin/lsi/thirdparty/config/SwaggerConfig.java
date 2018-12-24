package com.tripoin.lsi.thirdparty.config;

import com.google.common.collect.Lists;
import com.wissensalt.shared.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created on 8/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Value("${swagger.vendor.name}")
    private String swaggerVendorName;

    @Value("${swagger.vendor.value}")
    private String swaggerVendorValue;

    @Value("${swagger.metadata.title}")
    private String metadataTitle;

    @Value("${swagger.metadata.description}")
    private String metadataDescription;

    @Value("${swagger.metadata.version}")
    private String metadataVersion;

    @Value("${swagger.metadata.tos}")
    private String metadataTos;

    @Value("${swagger.metadata.contact.name}")
    private String contactName;

    @Value("${swagger.metadata.contact.url}")
    private String contactUrl;

    @Value("${swagger.metadata.contact.email}")
    private String contactEmail;

    @Value("${swagger.metadata.license}")
    private String metadataLicence;

    @Value("${swagger.metadata.license-url}")
    private String metadataLicenceUrl;

    @Bean
    public Docket oauthDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("LSI Core")
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .select()
                .paths(PathSelectors.regex("/secured.*"))
                .apis(RequestHandlerSelectors.any())
                .build().directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }


    @Bean
    public Docket actuatorDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ACTUATOR")
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .select()
                .paths(PathSelectors.regex("/actuator.*"))
                .apis(RequestHandlerSelectors.any())
                .build().directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket publicDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("PUBLIC")
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .select()
                .paths(PathSelectors.regex("/echo.*"))
                .apis(RequestHandlerSelectors.any())
                .build().directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        List<VendorExtension> vendorExtensions = new ArrayList<>();
        vendorExtensions.add(new AppVendorExtension());
        return new ApiInfo(
                metadataTitle,
                metadataDescription,
                metadataVersion,
                metadataTos,
                new Contact(contactName, contactUrl, contactEmail),
                metadataLicence,
                metadataLicenceUrl,
                vendorExtensions);
    }

    private class AppVendorExtension implements VendorExtension<String>{

        @Override
        public String getName() {
            return swaggerVendorName;
        }

        @Override
        public String getValue() {
            return swaggerVendorValue;
        }
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder
                .builder()
                .scopeSeparator(CommonConstant.Punctuation.COMMA)
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", CommonConstant.Security.AUTHORIZATION, CommonConstant.Security.HEADER);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
    }

}
