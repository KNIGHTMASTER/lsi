package com.tripoin.lsi.thirdparty.endpoint;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseSummaryUserAnswerDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Answer End Point", value = "Api Scaffolding Interview Answer", description = "Api Scaffolding for Entity Interview Answer")
@RequestMapping("/secured/interviewAnswer")
public interface IInterviewUserAnswerEndPoint extends IScaffoldingEndPoint<RequestInterviewUserAnswerDTO, ResponseInterviewUserAnswerDTO> {

    @ApiOperation(value = "Find Summary Answer")
    @PostMapping("/findSummaryAnswer")
    Page<ResponseSummaryUserAnswerDTO> findSummaryUserAnswerDTO(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestSummaryUserAnswerDTO p_RequestSummaryUserAnswerDTO) throws EndPointException;
}
