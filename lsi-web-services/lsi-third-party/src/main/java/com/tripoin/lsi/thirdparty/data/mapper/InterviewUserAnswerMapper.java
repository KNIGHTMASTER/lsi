package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewUserAnswer;
import com.tripoin.lsi.thirdparty.service.IQuestionAnswerService;
import com.tripoin.lsi.thirdparty.service.IQuestionService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewUserAnswerMapper extends ADATAMapper<InterviewUserAnswer, ResponseInterviewUserAnswerDTO> {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IQuestionAnswerService answerService;

    @Autowired
    private QuestionAnswerMapper answerMapper;

    @Autowired
    private IQuestionService questionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewUserAnswerMapper.class);

    @Override
    public ResponseInterviewUserAnswerDTO convert(InterviewUserAnswer interviewUserAnswer) {
        ResponseInterviewUserAnswerDTO result = new ResponseInterviewUserAnswerDTO();
        result.setId(interviewUserAnswer.getId());
        result.setCode(interviewUserAnswer.getCode());
        result.setName(interviewUserAnswer.getName());
        result.setRemarks(interviewUserAnswer.getRemarks());
        result.setStatus(interviewUserAnswer.getStatus());
        if (interviewUserAnswer.getInterviewQuestionId() != null) {
            try {
                result.setQuestion(questionMapper.convert(questionService.findById(interviewUserAnswer.getInterviewQuestionId())));
                result.setAnswer(answerMapper.convert(answerService.findById(interviewUserAnswer.getQuestionAnswerId())));
                result.setOtherAnswer(interviewUserAnswer.getOtherAnswer());
            } catch (ServiceException e) {
                LOGGER.error("Error Get User Answer {} ", e.toString());
            }
        }
        return result;
    }
}
