package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewHeaderDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewHeader;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewHeaderMapper extends ADATAMapper<InterviewHeader, ResponseInterviewHeaderDTO> {

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Override
    public ResponseInterviewHeaderDTO convert(InterviewHeader p_InterviewHeader) {
        ResponseInterviewHeaderDTO response = new ResponseInterviewHeaderDTO();
        response.setId(p_InterviewHeader.getId());
        response.setCode(p_InterviewHeader.getCode());
        response.setName(p_InterviewHeader.getName());
        response.setRemarks(p_InterviewHeader.getRemarks());
        response.setStatus(p_InterviewHeader.getStatus());
        response.setProvinceId(p_InterviewHeader.getProvinceId());
        response.setCityId(p_InterviewHeader.getCityId());
        response.setDistrictId(p_InterviewHeader.getDistrictId());
        response.setVillageId(p_InterviewHeader.getVillageId());
        response.setNumberOfRespondent(p_InterviewHeader.getNumberOfRespondent());
        if (p_InterviewHeader.getInterviewEvent() != null) {
            response.setInterviewEvent(interviewEventMapper.convert(p_InterviewHeader.getInterviewEvent()));
        }
        if (p_InterviewHeader.getBranchId() != null) {
            response.setBranchId(p_InterviewHeader.getBranchId());
        }
        return response;
    }
}
