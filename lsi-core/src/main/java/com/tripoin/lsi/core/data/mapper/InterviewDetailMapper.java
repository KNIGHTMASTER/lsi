package com.tripoin.lsi.core.data.mapper;

import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewHeaderDTO;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewDetailMapper extends ADATAMapper<InterviewDetail, ResponseInterviewDetailDTO> {

    @Autowired
    private InterviewHeaderMapper interviewHeaderMapper;

    @Override
    public ResponseInterviewDetailDTO convert(InterviewDetail p_InterviewDetail) {
        ResponseInterviewDetailDTO response = new ResponseInterviewDetailDTO();
        response.setId(p_InterviewDetail.getId());
        response.setCode(p_InterviewDetail.getCode());
        response.setName(p_InterviewDetail.getName());
        response.setRemarks(p_InterviewDetail.getRemarks());
        response.setStatus(p_InterviewDetail.getStatus());
        response.setLatitude(p_InterviewDetail.getLatitude());
        response.setLongitude(p_InterviewDetail.getLongitude());
        response.setInterviewStatus(p_InterviewDetail.getInterviewStatus());
        response.setRespondentName(p_InterviewDetail.getRespondentName());
        response.setRespondentAge(p_InterviewDetail.getRespondentAge());
        response.setRespondentReligion(p_InterviewDetail.getRespondentReligion());
        if (p_InterviewDetail.getInterviewHeader() != null) {
            ResponseInterviewHeaderDTO headerDTO = interviewHeaderMapper.convert(p_InterviewDetail.getInterviewHeader());
            response.setInterviewHeader(headerDTO);
        }
        return response;
    }
}
