package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestParameterDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseParameterDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Parameter End Point", value = "Api Scaffolding Parameter", description = "Api Scaffolding for Entity Parameter")
@RequestMapping("/secured/parameter")
public interface IParameterEndPoint extends IScaffoldingEndPoint<RequestParameterDTO, ResponseParameterDTO> {

    @ResponseBody
    @GetMapping(value = "/lovParameterByGroup")
    @ApiOperation(value = "Select List Of Value", notes = "Find All Record From Entity Response as List Of Value",  response = GenericListResponseDTO.class)
    GenericListResponseDTO selectLOV(HttpServletRequest p_HttpServletRequest, Long p_GroupId) throws EndPointException;

    @ResponseBody
    @PostMapping(value = "/findParametersByGroupCode")
    @ApiOperation(value = "Find Parameters By Group Code", notes = "Find All Parameters By Group Code",  response = GenericListResponseDTO.class)
    GenericListResponseDTO<BaseResponseDTO> findParametersByGroupCode(HttpServletRequest httpServletRequest, @RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException;

    @ResponseBody
    @PostMapping(value = "/updateBulkParameter")
    @ApiOperation(value = "Update Bulk Parameter", notes = "Update Bulk Parameter",  response = ResponseData.class)
    ResponseData updateBulkParameter(HttpServletRequest httpServletRequest, @RequestBody Map<Long, String> p_MapUpdateParameter) throws EndPointException;
}
