package com.wissensalt.acl.config;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant.RoleName;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created on 8/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
public class SecurityOAuthConfig extends ResourceServerConfigurerAdapter {

        @Bean
        public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
                return new OAuth2ClientContextFilter();
        }

        @Bean
        public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
                FilterRegistrationBean registration = new FilterRegistrationBean();
                registration.setFilter(filter);
                registration.setOrder(-100);
                return registration;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
                http
                        .exceptionHandling()
                /*.accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(oAuth2AuthenticationEntryPoint)*/
                        .and()
                        .logout()
                        .logoutUrl("/oauth/logout")
                /*.logoutSuccessHandler(oAuthLogoutSuccessHandler)*/
                        .and()
                        .csrf()
                        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                        .disable()
                        .headers()
                        .frameOptions().disable()
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        .antMatchers("/secured/branch/selectLov").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA, RoleName.THIRD_PARTY)
                        .antMatchers("/secured/province/selectLov").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA, RoleName.THIRD_PARTY)
                        .antMatchers("/secured/city/selectLov").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA, RoleName.THIRD_PARTY)
                        .antMatchers("/secured/district/selectLov").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA, RoleName.THIRD_PARTY)
                        .antMatchers("/secured/village/selectLov").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA, RoleName.THIRD_PARTY)
                        .antMatchers("/hello/").permitAll()
                        .antMatchers("/secured/menu/**").hasAnyRole(RoleName.ADMIN, RoleName.DIREKSI, RoleName.SPV_PROVINSI, RoleName.KOORDINATOR_PROVINSI, RoleName.KOORDINATOR_KOTA)
                        .antMatchers("/secured/**").authenticated()
                        .antMatchers("/principal/**").authenticated()
                        .antMatchers(
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/**",
                                "/swagger-ui.html",
                                "/webjars/**")
                        .permitAll();
        }
}
