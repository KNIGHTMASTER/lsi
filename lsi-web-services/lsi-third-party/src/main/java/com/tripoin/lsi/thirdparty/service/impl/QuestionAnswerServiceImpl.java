package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.dao.IQuestionAnswerDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestQuestionAnswerDTO;
import com.tripoin.lsi.thirdparty.data.model.Question;
import com.tripoin.lsi.thirdparty.data.model.QuestionAnswer;
import com.tripoin.lsi.thirdparty.service.IQuestionAnswerService;
import com.tripoin.lsi.thirdparty.service.IQuestionService;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class QuestionAnswerServiceImpl extends AScaffoldingService<QuestionAnswer, RequestQuestionAnswerDTO> implements IQuestionAnswerService {

    @Autowired
    private IQuestionAnswerDAO questionAnswerDAO;

    @Autowired
    private IQuestionService questionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = questionAnswerDAO;
    }

    @Override
    public QuestionAnswer generateEntityForCommonTrx(RequestQuestionAnswerDTO p_RequestQuestionAnswerDTO) throws ServiceException {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer = matchBaseFields(questionAnswer, p_RequestQuestionAnswerDTO);
        Question question = questionService.findById(p_RequestQuestionAnswerDTO.getQuestionId());
        if (question != null) {
            questionAnswer.setQuestion(question);
        }
        return questionAnswer;
    }

    @Override
    public List<QuestionAnswer> findByQuestionIdOrderByName(Long p_QuestionId) throws ServiceException {
        try {
            return questionAnswerDAO.findByQuestion_IdOrderByName(p_QuestionId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Question Id and Name {}", e.toString());
            return null;
        }
    }
}
