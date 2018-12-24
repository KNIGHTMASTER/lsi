package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInsertComponentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewComponentDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Component End Point", value = "Api Scaffolding Interview Component", description = "Api Scaffolding for Entity Interview Component")
@RequestMapping("/secured/interviewComponent")
public interface IInterviewComponentEndPoint extends IScaffoldingEndPoint<RequestInterviewComponentDTO, ResponseInterviewComponentDTO> {

    @ApiOperation(value = "Component Detail")
    @PostMapping("/getDetailByEvent")
    List<ResponseDetailComponentDTO> findComponentDetailByEventId(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException;

    @ApiOperation(value = "Insert Component Completely")
    @PostMapping("/insertComponentCompletely")
    ResponseData insertComponentCompletely(HttpServletRequest httpServletRequest, @RequestBody RequestInsertComponentDTO p_RequestInsertComponentDTO) throws EndPointException;
}
