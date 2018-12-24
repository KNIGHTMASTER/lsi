package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IQuestionDAO;
import com.tripoin.lsi.core.data.dto.request.RequestQuestionDTO;
import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.tripoin.lsi.core.data.model.Question;
import com.tripoin.lsi.core.service.IInterviewComponentService;
import com.tripoin.lsi.core.service.IQuestionService;
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
public class QuestionServiceImpl extends AScaffoldingService<Question, RequestQuestionDTO> implements IQuestionService {

    @Autowired
    private IQuestionDAO questionDAO;

    @Autowired
    private IInterviewComponentService interviewComponentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = questionDAO;
    }

    @Override
    public Question generateEntityForCommonTrx(RequestQuestionDTO p_RequestQuestionDTO) throws ServiceException {
        Question question = new Question();
        question = matchBaseFields(question, p_RequestQuestionDTO);
        if (question.getInterviewComponent() != null) {
            InterviewComponent interviewComponent = interviewComponentService.findById(p_RequestQuestionDTO.getInterviewComponentId());
            if (interviewComponent != null) {
                question.setInterviewComponent(interviewComponent);
            }
        }
        question.setQuestionType(p_RequestQuestionDTO.getQuestionType());
        return question;
    }

    @Override
    public List<Question> findByComponentId(Long p_ComponentId) throws ServiceException {
        try {
            return questionDAO.findByInterviewComponent_id(p_ComponentId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Interview Component Id {}", e.toString());
            return null;
        }
    }
}
