package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInsertEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewEventDTO;
import com.tripoin.lsi.core.data.dto.request.RequestUpdateEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailEventAndCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewEventDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Event End Point", value = "Api Scaffolding Interview Event", description = "Api Scaffolding for Entity Interview Event")
@RequestMapping("/secured/interviewEvent")
public interface IInterviewEventEndPoint extends IScaffoldingEndPoint<RequestInterviewEventDTO, ResponseInterviewEventDTO> {

    @ApiOperation(value = "Insert Event Include Candidates")
    @PostMapping("/insertEventCandidate")
    ResponseData insertEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestInsertEventCandidateDTO p_RequestInsertEventCandidateDTO) throws EndPointException;

    @ApiOperation(value = "Update Event Include Candidates")
    @PostMapping("/updateEventCandidate")
    ResponseData updateEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestUpdateEventCandidateDTO p_RequestUpdateEventCandidateDTO) throws EndPointException;

    @ApiOperation(value = "Detail Event Candidate")
    @PostMapping("/detailsEventCandidate")
    ResponseDetailEventAndCandidateDTO findDetailEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException;

    @ApiOperation(value = "Delete Event Completely")
    @PostMapping("/deleteEventCompletely")
    ResponseData deleteEventCompletely(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException;

    @ApiOperation(value = "Lov Active Event")
    @PostMapping("/selectLovActiveEvent")
    GenericListResponseDTO selectLOVActiveEvent(HttpServletRequest httpServletRequest) throws EndPointException;

    @ApiOperation(value = "Find Active Event By Agent Id")
    @PostMapping("/findActiveEventByAgentId")
    ResponseInterviewEventDTO findActiveEventByAgentId(HttpServletRequest p_HttpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException;
}
