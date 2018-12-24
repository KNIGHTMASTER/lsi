package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewHeaderDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByStatusDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByVolunteerDTO;
import com.tripoin.lsi.core.data.dto.response.*;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Header End Point", value = "Api Scaffolding Interview Header", description = "Api Scaffolding for Entity Interview Header")
@RequestMapping("/secured/interviewHeader")
public interface IInterviewHeaderEndPoint extends IScaffoldingEndPoint<RequestInterviewHeaderDTO, ResponseInterviewHeaderDTO> {

    @ApiOperation(value = "Interview Progress By Status")
    @PostMapping("/interviewProgressByStatus")
    Page<ContentInterviewProgressDTO> findInterviewProgressByStatus(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByStatusDTO p_RequestInterviewProgressByStatusDTO) throws EndPointException;

    @ApiOperation(value = "Interview Detail by User Name")
    @PostMapping("/interviewDetailByRespondentName")
    ResponseDetailInterviewDTO findInterviewDetail(HttpServletRequest p_HttpServletRequest, @RequestBody RequestFindByNameDTO requestFindByNameDTO) throws EndPointException;

    @ApiOperation(value = "Interview Progress By Volunteer")
    @PostMapping("/interviewProgressByVolunteer")
    Page<ContentVolunteerProgressDTO> findInterviewProgressByVolunteer(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByVolunteerDTO p_RequestInterviewProgressByVolunteerDTO) throws EndPointException;

    @ApiOperation(value = "Interview Progress By Area")
    @PostMapping("/interviewProgressByArea")
    Page<ContentAreaProgressDTO> findInterviewProgressByArea(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO) throws EndPointException;


    @ApiOperation(value = "Interview Progress By Area")
    @PostMapping("/findProvinceLOVByBranch")
    List<LOVBaseResponseDTO> findProvinceLOVByBranch(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody Long branchId) throws EndPointException;
}
