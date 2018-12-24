package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestEventCandidateDTO;
import com.tripoin.lsi.core.data.model.EventCandidate;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;

import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IEventCandidateService extends IScaffoldingService<EventCandidate, RequestEventCandidateDTO> {

    List<EventCandidate> findByInterviewEventId(Long p_InterviewEventId) throws ServiceException;

    List<Long> findNotIn(List<Long> p_CandicateIds, Long p_EventId) throws ServiceException;

    Long findByInterviewEventIdAndCandidateOrder(Long p_InterviewEvent, Integer p_CandidateOrder) throws ServiceException;
}
