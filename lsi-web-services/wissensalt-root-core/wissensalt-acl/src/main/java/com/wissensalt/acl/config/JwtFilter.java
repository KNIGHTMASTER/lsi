package com.wissensalt.acl.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissensalt.scaffolding.util.JwtTokenUtil;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 7/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.cookie}")
    private String jwtCookieName;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String userName = JwtTokenUtil.getSubject(httpServletRequest, jwtCookieName, jwtSecret);
        if(userName == null){
            LOGGER.warn("Username not found");
            ResponseData responseData = new ResponseData(EResponseCode.RC_UNAUTHORIZED.getResponseCode(), EResponseCode.RC_UNAUTHORIZED.getResponseMsg());
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().println(objectMapper.writeValueAsString(responseData));
        } else{
            httpServletRequest.setAttribute("username", userName);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
