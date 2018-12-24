package com.wissensalt.api.logger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/26/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

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
    public Docket apiLogger(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api Logger")
                .apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wissensalt.api.logger"))
                .paths(PathSelectors.regex("/api.*"))
                .build();
    }

    private ApiInfo metaData() {
        List<VendorExtension> vendorExtensions = new ArrayList<>();
        vendorExtensions.add(new SampleVendorExtension());
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


    private class SampleVendorExtension implements VendorExtension<String>{

        @Override
        public String getName() {
            return swaggerVendorName;
        }

        @Override
        public String getValue() {
            return swaggerVendorValue;
        }
    }
}
