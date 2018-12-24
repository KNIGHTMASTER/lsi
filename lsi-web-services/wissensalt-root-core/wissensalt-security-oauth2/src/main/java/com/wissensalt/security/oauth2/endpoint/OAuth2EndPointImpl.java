package com.wissensalt.security.oauth2.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.auth.IAuthEndPoint;
import com.wissensalt.scaffolding.service.core.IAuthenticationService;
import com.wissensalt.scaffolding.service.core.IBranchService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.request.RequestLoginDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.security.ResponseTokenDTO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * Created on 8/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class OAuth2EndPointImpl implements IAuthEndPoint<RequestLoginDTO, ResponseTokenDTO>{

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private IBranchService branchService;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2EndPointImpl.class);

    @RequestLogger(name = "oauth2|logout")
    @Override
    public ResponseData logout(HttpServletRequest p_Request, HttpServletResponse p_Response) throws EndPointException {
        ResponseData result = new ResponseData(EResponseCode.RC_LOGOUT_FAILED.getResponseCode(), EResponseCode.RC_LOGOUT_FAILED.getResponseMsg());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            authenticationService.logout(p_Request, p_Response, auth);
            try {
                authenticationService.updateStatusLoggedOut(auth.getName());
            } catch (ServiceException e) {
                LOGGER.error("Update Status Logged Out Failed");
            }
            LOGGER.info("Logout Successful");
            try {
                authenticationService.updateStatusLoggedOut(auth.getName());
            } catch (ServiceException e) {
                LOGGER.error("Error Update Status Logged Out {}", e.toString());
            }
            result = new ResponseData(EResponseCode.RC_LOGOUT_SUCCESS.getResponseCode(), EResponseCode.RC_LOGOUT_SUCCESS.getResponseMsg());
        }else {
            LOGGER.error("Logout Failed");
        }
        return result;
    }

    @RequestLogger(name = "oauth2|client-detail")
    @ApiOperation(value = "Find OAuth Detail By Tenant Code")
    @PostMapping("/findClientDetailByTenantCode")
    String findClientDetailTenant(@RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException {
        try {
            try {
                return Base64.getEncoder().encodeToString(mapper.writeValueAsString(branchService.findClientDetailTenant(p_RequestFindByCodeDTO)).getBytes());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Mapping Json DATA {}", e.toString());
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Find Client Detail Tenant {}", e.toString());
            return null;
        }
    }

    @Override
    public ResponseTokenDTO login(@RequestBody RequestLoginDTO requestLoginDTO, HttpServletResponse p_Response, HttpServletRequest p_Request) throws EndPointException {
        return null;
    }
}
