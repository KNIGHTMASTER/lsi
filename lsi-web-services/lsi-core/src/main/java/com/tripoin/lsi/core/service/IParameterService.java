package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestParameterDTO;
import com.tripoin.lsi.core.data.model.Parameter;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IParameterService extends IScaffoldingService<Parameter, RequestParameterDTO> {

    List<Parameter> selectLOVByGroup(Long p_GroupId) throws ServiceException;

    GenericListResponseDTO<BaseResponseDTO> findParametersByGroupCode(RequestFindByCodeDTO p_RequestFindByCodeDTO) throws ServiceException;

    String getEventImagePath() throws ServiceException;

    String getComponentImagePath() throws ServiceException;

    String getNodeImagePath() throws ServiceException;

    String getUploadPath() throws ServiceException;

    ResponseData updateBulkParameter(Map<Long, String> p_MapUpdateParameter) throws ServiceException;
}
