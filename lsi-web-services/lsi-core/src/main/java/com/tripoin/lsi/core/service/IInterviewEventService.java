package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestInsertEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewEventDTO;
import com.tripoin.lsi.core.data.dto.request.RequestUpdateEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailEventAndCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewEventDTO;
import com.tripoin.lsi.core.data.model.InterviewEvent;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewEventService extends IScaffoldingService<InterviewEvent, RequestInterviewEventDTO> {

    @Transactional
    ResponseData insertEventCandidate(RequestInsertEventCandidateDTO p_RequestInsertEventCandidateDTO) throws ServiceException;

    ResponseDetailEventAndCandidateDTO findDetailEventCandidate(Long p_EventId) throws ServiceException;

    @Transactional
    ResponseData updateEventCandidate(RequestUpdateEventCandidateDTO p_RequestUpdateEventCandidateDTO) throws ServiceException;

    @Transactional
    ResponseData deleteEventCompletely(RequestFindByIdDTO p_RequestFindByIdDTO) throws ServiceException;

    public List<InterviewEvent> selectLOVActiveEvent() throws ServiceException;

    ResponseInterviewEventDTO findActiveEventByAgentId(Long p_AgentId) throws ServiceException;

    InterviewEvent checkIsEventActive(Long p_EventId) throws ServiceException;
}
