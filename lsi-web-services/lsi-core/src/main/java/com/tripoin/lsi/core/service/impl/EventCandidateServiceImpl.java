package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IEventCandidateDAO;
import com.tripoin.lsi.core.data.dto.request.RequestEventCandidateDTO;
import com.tripoin.lsi.core.data.model.EventCandidate;
import com.tripoin.lsi.core.service.IEventCandidateService;
import com.tripoin.lsi.core.service.IInterviewEventService;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class EventCandidateServiceImpl extends AScaffoldingService<EventCandidate, RequestEventCandidateDTO> implements IEventCandidateService {

    @Autowired
    private IEventCandidateDAO eventCandidateDAO;

    @Autowired
    private IInterviewEventService interviewEventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventCandidateServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = eventCandidateDAO;
    }

    @Override
    public EventCandidate generateEntityForCommonTrx(RequestEventCandidateDTO requestEventCandidateDTO) throws ServiceException {
        EventCandidate eventCandidate = new EventCandidate();
        eventCandidate = matchBaseFields(eventCandidate, requestEventCandidateDTO);
        if (requestEventCandidateDTO.getInterviewEventId() != null) {
            eventCandidate.setInterviewEvent(interviewEventService.findById(requestEventCandidateDTO.getInterviewEventId()));
        }
        eventCandidate.setCandidateOrderNumber(requestEventCandidateDTO.getCandidateOrderNumber());
        return eventCandidate;
    }

    @Override
    public List<EventCandidate> findByInterviewEventId(Long p_InterviewEventId) throws ServiceException {
        try {
            return eventCandidateDAO.findByInterviewEvent_id(p_InterviewEventId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Interview Event Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<Long> findNotIn(List<Long> p_CandicateIds, Long p_EventId) throws ServiceException {
        try {
            return eventCandidateDAO.findNotIn(p_CandicateIds, p_EventId);
        } catch (DAOException e) {
            LOGGER.error("Error Find Not In {}", e.toString());
            return null;
        }
    }

    @Override
    public Long findByInterviewEventIdAndCandidateOrder(Long p_InterviewEvent, Integer p_CandidateOrder) throws ServiceException {
        try {
            EventCandidate eventCandidate = eventCandidateDAO.findByInterviewEvent_idAndCandidateOrderNumber(p_InterviewEvent, p_CandidateOrder);
            if (eventCandidate != null) {
                return eventCandidate.getId();
            }else {
                return null;
            }
        } catch (DAOException e) {
            LOGGER.error("Error findByInterviewEventIdAndCandidateOrder {}", e.toString());
            return null;
        }
    }
}