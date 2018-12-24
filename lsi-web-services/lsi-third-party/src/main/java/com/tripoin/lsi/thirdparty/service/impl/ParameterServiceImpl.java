package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.LsiThirdPartyConstant;
import com.tripoin.lsi.thirdparty.dao.IParameterDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestParameterDTO;
import com.tripoin.lsi.thirdparty.data.model.Parameter;
import com.tripoin.lsi.thirdparty.data.model.ParameterGroup;
import com.tripoin.lsi.thirdparty.service.IParameterGroupService;
import com.tripoin.lsi.thirdparty.service.IParameterService;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ParameterServiceImpl extends AScaffoldingService<Parameter, RequestParameterDTO> implements IParameterService {

    @Autowired
    private IParameterDAO parameterDAO;

    @Autowired
    private IParameterGroupService parameterGroupService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = parameterDAO;
    }

    @Override
    public Parameter generateEntityForCommonTrx(RequestParameterDTO requestParameterDTO) throws ServiceException {
        Parameter parameter = new Parameter();
        parameter = matchBaseFields(parameter, requestParameterDTO);
        if (parameter.getParameterGroup() != null) {
            ParameterGroup parameterGroup = parameterGroupService.findById(requestParameterDTO.getParameterGroupId());
            if (parameterGroup != null) {
                parameter.setParameterGroup(parameterGroup);
            }
        }
        return parameter;
    }

    @Override
    public List<Parameter> selectLOVByGroup(Long p_GroupId) throws ServiceException {
        try {
            return parameterDAO.selectLOVByGroup(p_GroupId);
        } catch (DAOException e) {
            LOGGER.error("Error Select LOV By Group {}", e.toString());
            return null;
        }
    }

    @Override
    public GenericListResponseDTO<BaseResponseDTO> findParametersByGroupCode(RequestFindByCodeDTO p_RequestFindByCodeDTO) throws ServiceException {
        GenericListResponseDTO<BaseResponseDTO> result = new GenericListResponseDTO<>();
        result.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
        List<Parameter> parameters = null;
        try {
            parameters =  parameterDAO.findByParameterGroup_Code(p_RequestFindByCodeDTO.getCode());
        } catch (DAOException e) {
            LOGGER.error("Error Find Parameter By Group Code {}", e.toString());
            result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        List<BaseResponseDTO> responseList = new ArrayList<>();
        if (parameters != null) {
            if (parameters.size() > 0) {
                for (Parameter parameter : parameters) {
                    BaseResponseDTO responseDTO = new BaseResponseDTO();
                    responseDTO.setId(parameter.getId());
                    responseDTO.setCode(parameter.getCode());
                    responseDTO.setName(parameter.getName());
                    responseDTO.setRemarks(parameter.getRemarks());
                    responseDTO.setStatus(parameter.getStatus());
                    responseList.add(responseDTO);
                }
            }
        }else {
            result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        result.setContent(responseList);
        return result;
    }

    @Override
    public String getUploadPath() throws ServiceException {
        String result = "";
        Parameter parameterIp = findByCode(LsiThirdPartyConstant.WebParameter.IP);
        Parameter parameterContextPath = findByCode(LsiThirdPartyConstant.WebParameter.CONTEXT_PATH);
        Parameter parameterUploadPath = findByCode(LsiThirdPartyConstant.WebParameter.UPLOAD_PATH);
        if (parameterIp != null) {
            result = result.concat(parameterIp.getName());
            if (parameterContextPath != null) {
                result = result.concat(parameterContextPath.getName());
                if (parameterUploadPath != null) {
                    result = result.concat(parameterUploadPath.getName());
                }
            }
        }
        return result;
    }

    @Override
    public String getEventImagePath() throws ServiceException {
        String result = "";
        Parameter parameterEvent = findByCode(LsiThirdPartyConstant.WebParameter.EVENT_PATH);
        if (parameterEvent != null) {
            result = getUploadPath().concat(parameterEvent.getName());
        }
        return result;
    }

    @Override
    public String getComponentImagePath() throws ServiceException {
        String result = "";
        Parameter parameterComponent = findByCode(LsiThirdPartyConstant.WebParameter.COMPONENT_PATH);
        if (parameterComponent != null) {
            result = getUploadPath().concat(parameterComponent.getName());
        }
        return result;
    }

    @Override
    public String getNodeImagePath() throws ServiceException {
        Parameter parameterNode = findByCode(LsiThirdPartyConstant.WebParameter.NODE_IMAGE_PATH);
        if (parameterNode != null) {
            return parameterNode.getName();
        }else {
            return "";
        }
    }

    @Override
    public ResponseData updateBulkParameter(Map<Long, String> p_MapUpdateParameter) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        for (Map.Entry<Long, String> map : p_MapUpdateParameter.entrySet()) {
            Parameter parameter = findById(map.getKey());
            if (parameter != null) {
                parameter.setName(map.getValue());
                update(parameter);
            }else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }
        return responseData;
    }
}