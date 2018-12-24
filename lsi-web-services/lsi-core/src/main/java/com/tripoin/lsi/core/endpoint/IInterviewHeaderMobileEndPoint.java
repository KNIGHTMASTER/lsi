package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInsertInterviewDetailResultDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringIncludeAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileFindDetailAssignmentByAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileInterviewComponent;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobilePointFilteringDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Header Mobile End Point", value = "Api Scaffolding Interview Header Mobile", description = "Api Scaffolding for Entity Interview Header Mobile")
@RequestMapping("/secured/interviewHeaderMobile")
public interface IInterviewHeaderMobileEndPoint {

    @ApiOperation(value = "Find Detail Assignment Agent")
    @GetMapping("/findDetailAssignmentAgent")
    ResponseMobileFindDetailAssignmentByAgentDTO findDetailAssignmentAgent(HttpServletRequest p_HttpServletRequest, Principal p_Principal) throws EndPointException;

    @ApiOperation(value = "Find Interview Component By Principal")
    @GetMapping("/findInterviewComponentByPrincipal")
    ResponseMobileInterviewComponent findInterviewComponentByPrincipal(HttpServletRequest p_HttpServletRequest, Principal p_Principal) throws EndPointException;

    @ApiOperation(value = "Find Point Filtering By Principal")
    @PostMapping("/findPointFiltering")
    ResponseMobilePointFilteringDTO findPointFiltering(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestMobilePointFilteringDTO p_RequestMobilePointFilteringDTO) throws EndPointException;

    @ApiOperation(value = "Find Point Filtering Include Agent")
    @PostMapping("/findPointFilteringIncludeAgent")
    ResponseMobilePointFilteringDTO findPointFilteringIncludeAgent(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestMobilePointFilteringIncludeAgentDTO p_RequestMobilePointFilteringIncludeAgentDTO) throws EndPointException;

    @ApiOperation(value = "Find Point Filtering Include Agent")
    @PostMapping("/insertInterviewDetailResult")
    ResponseData insertInterviewDetailResult(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestInsertInterviewDetailResultDTO p_RequestInsertInterviewDetailResultDTO) throws EndPointException;
}
