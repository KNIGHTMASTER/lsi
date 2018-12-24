package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseQuestionDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseQuestionDetailDTO;
import com.tripoin.lsi.thirdparty.data.model.Question;
import com.tripoin.lsi.thirdparty.data.model.QuestionAnswer;
import com.tripoin.lsi.thirdparty.service.IQuestionAnswerService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.response.base.ResponseIdNameDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class QuestionMapper extends ADATAMapper<Question, ResponseQuestionDTO> {

    @Autowired
    private InterviewComponentMapper interviewComponentMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private IQuestionAnswerService questionAnswerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionMapper.class);
    @Override
    public ResponseQuestionDTO convert(Question p_Question) {
        ResponseQuestionDTO result = new ResponseQuestionDTO();
        result.setId(p_Question.getId());
        result.setCode(p_Question.getCode());
        result.setName(p_Question.getName());
        result.setRemarks(p_Question.getRemarks());
        result.setStatus(p_Question.getStatus());
        if (p_Question.getInterviewComponent() != null) {
            result.setInterviewComponent(interviewComponentMapper.convert(p_Question.getInterviewComponent()));
        }
        result.setQuestionType(p_Question.getQuestionType());
        return result;
    }

    public ResponseQuestionDetailDTO convertDetail(Question p_Question) {
        ResponseQuestionDetailDTO response = new ResponseQuestionDetailDTO();
        response.setQuestionId(p_Question.getId());
        response.setQuestionName(p_Question.getName());
        response.setQuestionType(p_Question.getQuestionType());
        List<ResponseIdNameDTO> questionAnswers = new ArrayList<>();
        List<QuestionAnswer> questionAnswersRaw = new ArrayList<>();
        try {
            questionAnswersRaw = questionAnswerService.findByQuestionIdOrderByName(p_Question.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error Find QA By Q Id Order By Name {}", e.toString());
        }
        for (QuestionAnswer questionAnswer : questionAnswersRaw) {
            questionAnswers.add(questionAnswerMapper.convertIdName(questionAnswer));
        }
        response.setQuestionAnswers(questionAnswers);
        return response;
    }
}
