package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewComponentDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewComponentDetailDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewComponent;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewComponentMapper extends ADATAMapper<InterviewComponent, ResponseInterviewComponentDTO> {

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private StatementImageMapper statementMapper;

    @Override
    public ResponseInterviewComponentDTO convert(InterviewComponent p_InterviewComponent) {
        ResponseInterviewComponentDTO result = new ResponseInterviewComponentDTO();
        result.setId(p_InterviewComponent.getId());
        result.setCode(p_InterviewComponent.getCode());
        result.setName(p_InterviewComponent.getName());
        result.setRemarks(p_InterviewComponent.getRemarks());
        result.setStatus(p_InterviewComponent.getStatus());
        if (p_InterviewComponent.getInterviewEvent() != null) {
            result.setInterviewEvent(interviewEventMapper.convert(p_InterviewComponent.getInterviewEvent()));
        }
        result.setComponentType(p_InterviewComponent.getComponentType());
        result.setComponentOrder(p_InterviewComponent.getComponentOrder());
        return result;
    }

    public ResponseInterviewComponentDetailDTO convertDetail(InterviewComponent p_InterviewComponent) {
        ResponseInterviewComponentDetailDTO response = new ResponseInterviewComponentDetailDTO();
        response.setComponentOrder(p_InterviewComponent.getComponentOrder());
        response.setComponentType(p_InterviewComponent.getComponentType());
        if (p_InterviewComponent.getQuestion() != null) {
            response.setQuestion(questionMapper.convertDetail(p_InterviewComponent.getQuestion()));
        }
        if (p_InterviewComponent.getStatementImage() != null) {
            response.setStatement(statementMapper.convertOnlyContent(p_InterviewComponent.getStatementImage()));
        }
        return response;
    }
}
