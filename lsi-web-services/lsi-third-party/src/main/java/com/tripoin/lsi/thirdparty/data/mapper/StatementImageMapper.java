package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseStatementImageDTO;
import com.tripoin.lsi.thirdparty.data.model.StatementImage;
import com.tripoin.lsi.thirdparty.service.IParameterService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class StatementImageMapper extends ADATAMapper<StatementImage, ResponseStatementImageDTO> {

    @Autowired
    private InterviewComponentMapper interviewComponentMapper;

    @Autowired
    private IParameterService parameterService;

    @Override
    public ResponseStatementImageDTO convert(StatementImage p_StatementImage) {
        ResponseStatementImageDTO response = new ResponseStatementImageDTO();
        response.setId(p_StatementImage.getId());
        response.setCode(p_StatementImage.getCode());
        response.setName(p_StatementImage.getName());
        response.setRemarks(p_StatementImage.getRemarks());
        response.setStatus(p_StatementImage.getStatus());
        if (p_StatementImage.getInterviewComponent() != null) {
            response.setInterviewComponent(interviewComponentMapper.convert(p_StatementImage.getInterviewComponent()));
        }
        return response;
    }

    public BaseResponseDTO convertBasic(StatementImage p_StatementImage) {
        ResponseStatementImageDTO response = new ResponseStatementImageDTO();
        response.setId(p_StatementImage.getId());
        response.setCode(p_StatementImage.getCode());
        response.setName(p_StatementImage.getName());
        response.setRemarks(p_StatementImage.getRemarks());
        response.setStatus(p_StatementImage.getStatus());
        return response;
    }

    public String convertOnlyContent(StatementImage p_StatementImage) {
        if (p_StatementImage.getName() != null) {
            try {
                return parameterService.getComponentImagePath().concat(p_StatementImage.getName());
            } catch (ServiceException e) {
                e.printStackTrace();
                return "";
            }
        }else {
            return "";
        }
    }
}
