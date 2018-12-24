package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInsertEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewEventDTO;
import com.tripoin.lsi.core.data.dto.request.RequestUpdateEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailEventAndCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewEventDTO;
import com.tripoin.lsi.core.data.mapper.InterviewEventMapper;
import com.tripoin.lsi.core.data.model.InterviewEvent;
import com.tripoin.lsi.core.endpoint.IInterviewEventEndPoint;
import com.tripoin.lsi.core.service.IInterviewEventService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewEventEndPointImpl extends AScaffoldingEndPoint<InterviewEvent, RequestInterviewEventDTO, ResponseInterviewEventDTO> implements IInterviewEventEndPoint {
    @Autowired
    private IInterviewEventService interviewEventService;

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewEventEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = interviewEventService;
        dataMapperIntegration = interviewEventMapper;
    }

    @RequestLogger(name = "Interview Event | Insert Event Candidate")
    @Override
    public ResponseData insertEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestInsertEventCandidateDTO p_RequestInsertEventCandidateDTO) throws EndPointException {
        ResponseData responseData;
        try {
            responseData =  interviewEventService.insertEventCandidate(p_RequestInsertEventCandidateDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Insert Event Candidate {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "Interview Event | Update Event Candidate")
    @Override
    public ResponseData updateEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestUpdateEventCandidateDTO p_RequestUpdateEventCandidateDTO) throws EndPointException {
        ResponseData responseData;
        try {
            responseData =  interviewEventService.updateEventCandidate(p_RequestUpdateEventCandidateDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Update Event Candidate {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "Interview Event | Find Detail Event Candidate")
    @Override
    public ResponseDetailEventAndCandidateDTO findDetailEventCandidate(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException {
        ResponseDetailEventAndCandidateDTO response = new ResponseDetailEventAndCandidateDTO();
        try {
            response =  interviewEventService.findDetailEventCandidate(p_RequestFindByIdDTO.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error Find Detail Event Candidate {}", e.toString());
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

    @RequestLogger(name = "Interview Event | Delete Event Completely")
    @Override
    public ResponseData deleteEventCompletely(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException {
        ResponseData responseData;
        try {
            responseData =  interviewEventService.deleteEventCompletely(p_RequestFindByIdDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Delete Event Completely {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "Interview Event | LOV Active Event")
    @Override
    public GenericListResponseDTO selectLOVActiveEvent(HttpServletRequest httpServletRequest) throws EndPointException {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            result = this.scaffoldingResponseBuilder.buildListDTOResponse(interviewEventService.selectLOVActiveEvent());
        } catch (ServiceException var4) {
            this.LOGGER.error("Error Searching LOV Data : {}", var4.toString());
        }

        return result;
    }

    @RequestLogger(name = "Interview Event | Find Active Event By Agent Id")
    @Override
    public ResponseInterviewEventDTO findActiveEventByAgentId(HttpServletRequest p_HttpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException {
        try {
            return interviewEventService.findActiveEventByAgentId(p_RequestFindByIdDTO.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error Find Active EVent By Agent Id {}", e.toString());
            return null;
        }
    }
}
