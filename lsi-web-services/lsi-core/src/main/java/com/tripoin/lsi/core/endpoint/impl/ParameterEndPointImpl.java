package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestParameterDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseParameterDTO;
import com.tripoin.lsi.core.data.mapper.ParameterMapper;
import com.tripoin.lsi.core.data.model.Parameter;
import com.tripoin.lsi.core.endpoint.IParameterEndPoint;
import com.tripoin.lsi.core.service.IParameterService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class ParameterEndPointImpl extends AScaffoldingEndPoint<Parameter, RequestParameterDTO, ResponseParameterDTO> implements IParameterEndPoint {
    @Autowired
    private IParameterService parameterService;

    @Autowired
    private ParameterMapper parameterMapper;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = parameterService;
        dataMapperIntegration = parameterMapper;
    }

    @Override
    public GenericListResponseDTO selectLOV(HttpServletRequest p_HttpServletRequest, Long p_GroupId) throws EndPointException {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            result = scaffoldingResponseBuilder.buildListDTOResponse(parameterService.selectLOVByGroup(p_GroupId));
        } catch (ServiceException e) {
            LOGGER.error("Error Searching LOV Data : {}", e.toString());
        }
        return result;
    }

    @RequestLogger(name = "parameter|find-parameter-by-group-code")
    @Override
    public GenericListResponseDTO<BaseResponseDTO> findParametersByGroupCode(HttpServletRequest httpServletRequest, @RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException {
        try {
            return parameterService.findParametersByGroupCode(p_RequestFindByCodeDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Parameters By Group Code {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "parameter|update-bulk-parameter")
    @Override
    public ResponseData updateBulkParameter(HttpServletRequest httpServletRequest, @RequestBody Map<Long, String> p_MapUpdateParameter) throws EndPointException {
        ResponseData responseData;
        try {
            responseData =  parameterService.updateBulkParameter(p_MapUpdateParameter);
        } catch (ServiceException e) {
            LOGGER.error("Error Update Bulk Parameter {}", e.toString());
            responseData =  new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }
}
