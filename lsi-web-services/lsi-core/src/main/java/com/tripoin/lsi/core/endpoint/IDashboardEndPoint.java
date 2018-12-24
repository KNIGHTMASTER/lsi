package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDashboardDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDashboardInterview;
import com.wissensalt.scaffolding.exception.EndPointException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Dashboard End Point", value = "Api Dashboard", description = "Api Dashboard")
@RequestMapping("/secured/dashboard")
public interface IDashboardEndPoint {

    @ApiOperation(value = "Dashboard for Interview Data")
    @PostMapping("/dashboardInterview")
    ResponseDashboardInterview getDashboardInterview(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewDashboardDTO p_RequestInterviewDashboardDTO) throws EndPointException;
}
