package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IInterviewEventDAO;
import com.tripoin.lsi.core.data.dto.request.*;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailEventAndCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewEventDTO;
import com.tripoin.lsi.core.data.mapper.EventCandidateMapper;
import com.tripoin.lsi.core.data.mapper.InterviewEventMapper;
import com.tripoin.lsi.core.data.model.EventCandidate;
import com.tripoin.lsi.core.data.model.InterviewEvent;
import com.tripoin.lsi.core.service.IEventCandidateService;
import com.tripoin.lsi.core.service.IInterviewEventService;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewEventServiceImpl extends AScaffoldingService<InterviewEvent, RequestInterviewEventDTO> implements IInterviewEventService {

    @Autowired
    private IInterviewEventDAO interviewEventDAO;

    @Autowired
    private IEventCandidateService eventCandidateService;

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Autowired
    private EventCandidateMapper eventCandidateMapper;

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewEventServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewEventDAO;
    }

    @Override
    public InterviewEvent generateEntityForCommonTrx(RequestInterviewEventDTO requestInterviewEventDTO) throws ServiceException {
        InterviewEvent interviewEvent = new InterviewEvent();
        interviewEvent = matchBaseFields(interviewEvent, requestInterviewEventDTO);
        try {
            interviewEvent.setEventStartDate(FormatDateConstant.DEFAULT.parse(requestInterviewEventDTO.getEventStartDate()));
            interviewEvent.setEventEndDate(FormatDateConstant.DEFAULT.parse(requestInterviewEventDTO.getEventEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.error("Error Parsing Date {}", e.toString());
        }
        interviewEvent.setIsVote(requestInterviewEventDTO.getIsVote());
        return interviewEvent;
    }

    @Override
    public ResponseData insertEventCandidate(RequestInsertEventCandidateDTO p_RequestInsertEventCandidateDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        if (p_RequestInsertEventCandidateDTO.getEventName() != null) {
            InterviewEvent interviewEvent = new InterviewEvent();
            interviewEvent.setIsVote(p_RequestInsertEventCandidateDTO.getIsVote());
            interviewEvent.setName(p_RequestInsertEventCandidateDTO.getEventName());
            if (p_RequestInsertEventCandidateDTO.getEventStartTime()!= null && p_RequestInsertEventCandidateDTO.getEventEndTime() != null) {
                try {
                    interviewEvent.setEventStartDate(FormatDateConstant.DEFAULT.parse(p_RequestInsertEventCandidateDTO.getEventStartTime()));
                    interviewEvent.setEventEndDate(FormatDateConstant.DEFAULT.parse(p_RequestInsertEventCandidateDTO.getEventEndTime()));
                } catch (ParseException e) {
                    LOGGER.error("Error Parsing Date {}", e.toString());
                }
            }
            insert(interviewEvent);
            if (interviewEvent.getId() != null) {
                for (ContentInsertCandidateDTO candidate : p_RequestInsertEventCandidateDTO.getCandidates()) {
                    EventCandidate eventCandidate = new EventCandidate();
                    eventCandidate.setName(candidate.getName());
                    eventCandidate.setCandidateImagePath(candidate.getCandidateImagePath());
                    eventCandidate.setInterviewEvent(interviewEvent);
                    eventCandidate.setCandidateOrderNumber(candidate.getCandidateOrderNumber());

                    eventCandidateService.insert(eventCandidate);
                }
            } else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @Override
    public ResponseDetailEventAndCandidateDTO findDetailEventCandidate(Long p_EventId) throws ServiceException {
        ResponseDetailEventAndCandidateDTO response = new ResponseDetailEventAndCandidateDTO();
        response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        if (p_EventId != null) {
            InterviewEvent interviewEvent = findById(p_EventId);
            if (interviewEvent != null) {
                ResponseInterviewEventDTO event = interviewEventMapper.convert(interviewEvent);
                event.setIsVote(interviewEvent.getIsVote());
                response.setEvent(event);
                List<EventCandidate> eventCandidates = eventCandidateService.findByInterviewEventId(event.getId());
                List<ResponseEventCandidateDTO> responseEventCandidates = new ArrayList<>();
                for (EventCandidate eventCandidate : eventCandidates) {
                    responseEventCandidates.add(eventCandidateMapper.convert(eventCandidate));
                }
                response.setCandidates(responseEventCandidates);
                response.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
            }
        }
        return response;
    }

    @Override
    public ResponseData updateEventCandidate(RequestUpdateEventCandidateDTO p_RequestUpdateEventCandidateDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        if (p_RequestUpdateEventCandidateDTO.getId() != null) {
            InterviewEvent interviewEvent = findById(p_RequestUpdateEventCandidateDTO.getId());
            if (interviewEvent != null) {
                interviewEvent.setName(p_RequestUpdateEventCandidateDTO.getEventName());
                interviewEvent.setIsVote(p_RequestUpdateEventCandidateDTO.getIsVote());
                if (p_RequestUpdateEventCandidateDTO.getEventStartTime() != null) {
                    try {
                        interviewEvent.setEventStartDate(FormatDateConstant.DEFAULT.parse(p_RequestUpdateEventCandidateDTO.getEventStartTime()));
                    } catch (ParseException e) {
                        LOGGER.error("Error Parsing Start Date {}",e.toString());
                    }
                }
                if (p_RequestUpdateEventCandidateDTO.getEventEndTime() != null) {
                    try {
                        interviewEvent.setEventEndDate(FormatDateConstant.DEFAULT.parse(p_RequestUpdateEventCandidateDTO.getEventEndTime()));
                    } catch (ParseException e) {
                        LOGGER.error("Error Parsing End Date {}", e.toString());
                    }
                }
                List<Long> requestIDs = new ArrayList<>();
                for (ContentUpdateCandidateDTO updateCandidateDTO : p_RequestUpdateEventCandidateDTO.getCandidates()) {
                    requestIDs.add(updateCandidateDTO.getId());
                }
                List<Long> deleteList = eventCandidateService.findNotIn(requestIDs, interviewEvent.getId());
                for (Long deleteId : deleteList) {
                    eventCandidateService.delete(deleteId);
                }
                for (ContentUpdateCandidateDTO updateCandidateDTO : p_RequestUpdateEventCandidateDTO.getCandidates()) {
                    if (updateCandidateDTO.getId() != null) {
                        EventCandidate eventCandidate = eventCandidateService.findById(updateCandidateDTO.getId());
                        if (eventCandidate != null) {
                            eventCandidate.setName(updateCandidateDTO.getName());
                            eventCandidate.setCandidateImagePath(updateCandidateDTO.getCandidateImagePath());
                            eventCandidate.setCandidateOrderNumber(updateCandidateDTO.getCandidateOrderNumber());
                            eventCandidateService.update(eventCandidate);
                        }else {
                            eventCandidate = new EventCandidate();
                            eventCandidate.setInterviewEvent(interviewEvent);
                            eventCandidate.setName(updateCandidateDTO.getName());
                            eventCandidate.setCandidateImagePath(updateCandidateDTO.getCandidateImagePath());
                            eventCandidate.setCandidateOrderNumber(updateCandidateDTO.getCandidateOrderNumber());
                            eventCandidateService.insert(eventCandidate);
                        }
                    }
                }
            }else {
                LOGGER.error("Event Not Found");
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }else {
            LOGGER.error("Id can not be NULL");
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @Override
    public ResponseData deleteEventCompletely(RequestFindByIdDTO p_RequestFindByIdDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        InterviewEvent interviewEvent = findById(p_RequestFindByIdDTO.getId());
        if (interviewEvent != null) {
            for (EventCandidate eventCandidate : interviewEvent.getEventCandidates()) {
                eventCandidateService.delete(eventCandidate.getId());
            }
            delete(interviewEvent.getId());
        }else {
            LOGGER.error("Interview Event is Not Found");
        }
        return responseData;
    }

    @Override
    public List<InterviewEvent> selectLOVActiveEvent() {
        try {
            return interviewEventDAO.findLOVEventActive(new Date());
        } catch (DAOException var2) {
            LOGGER.error("Logger Select LOV Active Event : {}", var2.toString());
            return null;
        }
    }

    @Override
    public ResponseInterviewEventDTO findActiveEventByAgentId(Long p_AgentId) throws ServiceException {
        List<Long> eventIds = interviewHeaderService.findDistinctEventIdByUserId(p_AgentId);
        InterviewEvent activeEvent = null;
        for (Long eventId : eventIds) {
            InterviewEvent interviewEvent = checkIsEventActive(eventId);
            if (interviewEvent != null) {
                activeEvent = interviewEvent;
                break;
            }
        }
        if (activeEvent != null) {
            return interviewEventMapper.convert(activeEvent);
        }else {
            return null;
        }
    }

    @Override
    public InterviewEvent checkIsEventActive(Long p_EventId) throws ServiceException {
        InterviewEvent result = findById(p_EventId);
        Date currentDate = new Date();
        if (result != null) {
            if (result.getEventStartDate().compareTo(currentDate) <= 0) {
                if (result.getEventEndDate().compareTo(currentDate) >= 0) {
                    return result;
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
