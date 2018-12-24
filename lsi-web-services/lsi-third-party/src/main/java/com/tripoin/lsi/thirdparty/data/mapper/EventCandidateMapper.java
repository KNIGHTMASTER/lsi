package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseEventCandidateDTO;
import com.tripoin.lsi.thirdparty.data.model.EventCandidate;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class EventCandidateMapper extends ADATAMapper<EventCandidate, ResponseEventCandidateDTO> {

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Override
    public ResponseEventCandidateDTO convert(EventCandidate eventCandidate) {
        ResponseEventCandidateDTO response = new ResponseEventCandidateDTO();
        response.setId(eventCandidate.getId());
        response.setCode(eventCandidate.getCode());
        response.setName(eventCandidate.getName());
        response.setRemarks(eventCandidate.getRemarks());
        response.setStatus(eventCandidate.getStatus());
        if (eventCandidate.getInterviewEvent() != null) {
            response.setInterviewEvent(interviewEventMapper.convert(eventCandidate.getInterviewEvent()));
        }
        response.setCandidateImagePath(eventCandidate.getCandidateImagePath());
        response.setCandidateOrderNumber(eventCandidate.getCandidateOrderNumber());
        return response;
    }

}
