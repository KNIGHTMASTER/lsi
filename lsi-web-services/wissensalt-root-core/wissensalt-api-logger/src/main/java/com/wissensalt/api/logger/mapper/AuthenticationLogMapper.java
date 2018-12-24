package com.wissensalt.api.logger.mapper;

import com.wissensalt.api.logger.entity.auth.AuthenticationData;
import com.wissensalt.api.logger.entity.auth.AuthenticationDetails;
import com.wissensalt.api.logger.entity.auth.AuthenticationLog;
import com.wissensalt.shared.dto.request.authevent.RequestAuthenticationLogDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.stereotype.Service;

/**
 * Created on 6/5/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class AuthenticationLogMapper extends ADATAMapper<RequestAuthenticationLogDTO, AuthenticationLog> {
    @Override
    public AuthenticationLog convert(RequestAuthenticationLogDTO requestAuthenticationLogDTO) {
        AuthenticationLog result = new AuthenticationLog();
        if (requestAuthenticationLogDTO != null) {
            result.setTimeStamp(requestAuthenticationLogDTO.getTimeStamp());
            result.setPrincipal(requestAuthenticationLogDTO.getPrincipal());
            result.setType(requestAuthenticationLogDTO.getType());

            if (requestAuthenticationLogDTO.getData() != null) {
                AuthenticationData authenticationData = new AuthenticationData();
                authenticationData.setRequestUrl(requestAuthenticationLogDTO.getData().getRequestUrl());
                authenticationData.setType(requestAuthenticationLogDTO.getType());
                authenticationData.setMessage(requestAuthenticationLogDTO.getData().getMessage());

                AuthenticationDetails authenticationDetails = new AuthenticationDetails();
                if (requestAuthenticationLogDTO.getData().getDetails() != null) {
                    authenticationDetails.setRemoteAddress(requestAuthenticationLogDTO.getData().getDetails().getRemoteAddress());
                    authenticationDetails.setSessionId(requestAuthenticationLogDTO.getData().getDetails().getSessionId());
                }
                authenticationData.setDetails(authenticationDetails);

                result.setData(authenticationData);
            }
            return result;
        }
        return null;
    }
}
