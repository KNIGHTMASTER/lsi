package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewEventDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewEvent;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.springframework.stereotype.Service;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewEventMapper extends ADATAMapper<InterviewEvent, ResponseInterviewEventDTO> {

    @Override
    public ResponseInterviewEventDTO convert(InterviewEvent interviewEvent) {
        ResponseInterviewEventDTO response = new ResponseInterviewEventDTO();
        response.setId(interviewEvent.getId());
        response.setCode(interviewEvent.getCode());
        response.setName(interviewEvent.getName());
        response.setStatus(interviewEvent.getStatus());
        response.setRemarks(interviewEvent.getRemarks());
        if (interviewEvent.getEventStartDate() != null) {
            response.setEventStartDate(FormatDateConstant.DEFAULT.format(interviewEvent.getEventStartDate()));
        }
        if (interviewEvent.getEventEndDate() != null) {
            response.setEventEndDate(FormatDateConstant.DEFAULT.format(interviewEvent.getEventEndDate()));
        }
        response.setIsVote(interviewEvent.getIsVote());
        return response;
    }
}
