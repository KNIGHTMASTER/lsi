package com.tripoin.lsi.core.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
                        .authorizeRequests().antMatchers("/hello/").permitAll()
                        .and()
                        .authorizeRequests().antMatchers("/secured/**").authenticated()
                        .antMatchers("/principal/**").authenticated()
                        .and()
                        .authorizeRequests()
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
