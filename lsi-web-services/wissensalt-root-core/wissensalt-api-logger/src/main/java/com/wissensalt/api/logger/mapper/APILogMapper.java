package com.wissensalt.api.logger.mapper;

import com.wissensalt.api.logger.entity.api.APILog;
import com.wissensalt.api.logger.entity.api.APIRequestData;
import com.wissensalt.api.logger.entity.api.APIResponseData;
import com.wissensalt.shared.dto.request.apilog.RequestAPILogDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.stereotype.Service;

/**
 * Created on 6/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class APILogMapper extends ADATAMapper<RequestAPILogDTO, APILog> {
    @Override
    public APILog convert(RequestAPILogDTO requestAPILogDTO) {
        APILog result = new APILog();
        result.setTimeStamp(requestAPILogDTO.getTimeStamp());
        result.setServiceName(requestAPILogDTO.getServiceName());

        APIRequestData requestData = new APIRequestData();
        if (requestAPILogDTO.getRequestData() != null) {
            requestData.setRemoteAddress(requestAPILogDTO.getRequestData().getRemoteAddress());
            requestData.setRemotePort(requestAPILogDTO.getRequestData().getRemotePort());
            requestData.setRemoteUser(requestAPILogDTO.getRequestData().getRemoteUser());
            requestData.setMethod(requestAPILogDTO.getRequestData().getMethod());
            requestData.setRequestUrl(requestAPILogDTO.getRequestData().getRequestUrl());
            requestData.setHeaders(requestAPILogDTO.getRequestData().getHeaders());
            requestData.setCookies(requestAPILogDTO.getRequestData().getCookies());
            requestData.setPayload(requestAPILogDTO.getRequestData().getPayload());

            result.setRequestData(requestData);
        }

        APIResponseData responseData = new APIResponseData();
        if (requestAPILogDTO.getResponseData() != null) {
            responseData.setHost(requestAPILogDTO.getResponseData().getHost());
            responseData.setBody(requestAPILogDTO.getResponseData().getBody());

            result.setResponseData(responseData);
        }

        return result;
    }
}
