package com.tripoin.lsi.core.data.mapper;

import com.tripoin.lsi.core.data.dto.response.ResponseQuestionAnswerDTO;
import com.tripoin.lsi.core.data.model.QuestionAnswer;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseIdNameDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class QuestionAnswerMapper extends ADATAMapper<QuestionAnswer, ResponseQuestionAnswerDTO> {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public ResponseQuestionAnswerDTO convert(QuestionAnswer p_QuestionAnswer) {
        ResponseQuestionAnswerDTO result = new ResponseQuestionAnswerDTO();
        result.setId(p_QuestionAnswer.getId());
        result.setCode(p_QuestionAnswer.getCode());
        result.setName(p_QuestionAnswer.getName());
        result.setRemarks(p_QuestionAnswer.getRemarks());
        result.setStatus(p_QuestionAnswer.getStatus());
        if (p_QuestionAnswer.getQuestion() != null) {
            result.setQuestion(questionMapper.convert(p_QuestionAnswer.getQuestion()));
        }
        return result;
    }

    public BaseResponseDTO convertBasic(QuestionAnswer p_QuestionAnswer) {
        ResponseQuestionAnswerDTO result = new ResponseQuestionAnswerDTO();
        result.setId(p_QuestionAnswer.getId());
        result.setCode(p_QuestionAnswer.getCode());
        result.setName(p_QuestionAnswer.getName());
        result.setRemarks(p_QuestionAnswer.getRemarks());
        result.setStatus(p_QuestionAnswer.getStatus());
        return result;
    }

    public String convertOnlyContent(QuestionAnswer p_QuestionAnswer) {
        return p_QuestionAnswer.getName();
    }

    public ResponseIdNameDTO convertIdName(QuestionAnswer p_QuestionAnswer) {
        ResponseIdNameDTO responseIdNameDTO = new ResponseIdNameDTO();
        responseIdNameDTO.setId(p_QuestionAnswer.getId());
        if (p_QuestionAnswer.getName() != null) {
            if (p_QuestionAnswer.getName().length() > 0) {
                responseIdNameDTO.setName(p_QuestionAnswer.getName().substring(2, p_QuestionAnswer.getName().length()));
            }
        }
        return responseIdNameDTO;
    }
}
