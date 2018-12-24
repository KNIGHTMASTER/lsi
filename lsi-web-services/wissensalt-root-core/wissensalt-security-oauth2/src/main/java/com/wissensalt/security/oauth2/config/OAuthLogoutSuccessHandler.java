package com.wissensalt.security.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.core.IAuthenticationService;
import com.wissensalt.security.oauth2.client.IUserClient;
import com.wissensalt.shared.constant.CommonConstant;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.base.ResponseNameDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 7/31/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Component
public class OAuthLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IAuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthLogoutSuccessHandler.class);

    @Value("${feign.acl.api.base-path}")
    private String aclBasePath;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ResponseData responseData;
        String token = httpServletRequest.getHeader(CommonConstant.Security.AUTHORIZATION);

        if (token != null && token.startsWith(CommonConstant.Security.BEARER_PREFIX)) {
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(CommonConstant.Punctuation.SPACE)[1]);

            System.out.println("TOKEN "+oAuth2AccessToken.getValue());
            IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclBasePath);
            ResponseNameDTO responseNameDTO = userClient.getPrincipal("Bearer "+oAuth2AccessToken.getValue());

            tokenStore.removeAccessToken(oAuth2AccessToken);
            responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
            LOGGER.info("token has been removed / invalidated");

            if (responseNameDTO != null) {
                if (responseNameDTO.getName() != null) {
                    try {
                        authenticationService.updateStatusLoggedOut(responseNameDTO.getName());
                    } catch (ServiceException e) {
                        LOGGER.error("Failed Logout {}" , e.toString());
                        responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                    }
                }
            }else {
                LOGGER.info("Authentication is Empty");
            }
        }else {
            responseData = new ResponseData("403", "Un Authorized");
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().println(objectMapper.writeValueAsString(responseData));
    }
}
