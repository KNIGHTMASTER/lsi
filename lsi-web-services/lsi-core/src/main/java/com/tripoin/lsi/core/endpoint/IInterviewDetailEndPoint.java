package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailByHeaderDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailByHeaderDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Detail End Point", value = "Api Scaffolding Interview Detail", description = "Api Scaffolding for Entity Interview Detail")
@RequestMapping("/secured/interviewDetail")
public interface IInterviewDetailEndPoint extends IScaffoldingEndPoint<RequestInterviewDetailDTO, ResponseInterviewDetailDTO> {

    @ApiOperation(value = "Insert Detail By Header Id")
    @PostMapping("/findByHeaderId")
    Page<ResponseInterviewDetailByHeaderDTO> findByHeaderId(HttpServletRequest httpServletRequest, @RequestBody RequestInterviewDetailByHeaderDTO p_RequestInterviewDetailByHeaderDTO) throws EndPointException;
}
