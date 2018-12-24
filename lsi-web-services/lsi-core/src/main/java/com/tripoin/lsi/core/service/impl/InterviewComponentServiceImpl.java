package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.LsiCoreConstant;
import com.tripoin.lsi.core.dao.IInterviewComponentDAO;
import com.tripoin.lsi.core.data.dto.request.ContentInsertComponentDTO;
import com.tripoin.lsi.core.data.dto.request.ContentInsertQuestionAnswerDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInsertComponentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ContentQuestionDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailComponentDTO;
import com.tripoin.lsi.core.data.mapper.QuestionAnswerMapper;
import com.tripoin.lsi.core.data.mapper.StatementImageMapper;
import com.tripoin.lsi.core.data.model.*;
import com.tripoin.lsi.core.service.*;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewComponentServiceImpl extends AScaffoldingService<InterviewComponent, RequestInterviewComponentDTO> implements IInterviewComponentService {

    @Autowired
    private IInterviewComponentDAO interviewComponentDAO;

    @Autowired
    private IInterviewEventService interviewEventService;

    @Autowired
    private StatementImageMapper statementImageMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IStatementImageService statementService;

    @Autowired
    private IQuestionAnswerService questionAnswerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewComponentServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewComponentDAO;
    }

    @Override
    public InterviewComponent generateEntityForCommonTrx(RequestInterviewComponentDTO p_RequestInterviewComponentDTO) throws ServiceException {
        InterviewComponent result = new InterviewComponent();
        result = matchBaseFields(result, p_RequestInterviewComponentDTO);
        if (p_RequestInterviewComponentDTO.getInterviewEventId() != null) {
            InterviewEvent interviewEvent = interviewEventService.findById(p_RequestInterviewComponentDTO.getInterviewEventId());
            if (interviewEvent != null) {
                result.setInterviewEvent(interviewEvent);
            }
        }
        result.setComponentOrder(p_RequestInterviewComponentDTO.getComponentOrder());
        result.setComponentType(p_RequestInterviewComponentDTO.getComponentType());
        return result;
    }


    @Override
    public List<InterviewComponent> findByInterviewEventId(Long p_InterviewEventId) throws ServiceException {
        try {
            return interviewComponentDAO.findByInterviewEvent_id(p_InterviewEventId);
        } catch (DAOException e) {
            LOGGER.error("Error Find Component By Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewComponent> findByInterviewEventIdOrderByComponentOrder(Long p_InterviewEventId) throws ServiceException {
        try {
            return interviewComponentDAO.findByInterviewEvent_idOrderByComponentOrder(p_InterviewEventId);
        } catch (DAOException e) {
            LOGGER.error("Error Find Component By Header Id Order By Component Order{}", e.toString());
            return null;
        }
    }

    @Override
    public List<ResponseDetailComponentDTO> findComponentDetailByEventId(Long p_InterviewEventId) throws ServiceException {
        List<ResponseDetailComponentDTO> components = new ArrayList<>();
        InterviewEvent interviewEvent = interviewEventService.findById(p_InterviewEventId);
        if (interviewEvent != null) {
            for (InterviewComponent interviewComponent : findByInterviewEventIdOrderByComponentOrder(p_InterviewEventId)) {
                ResponseDetailComponentDTO component = new ResponseDetailComponentDTO();
                component.setId(interviewComponent.getId());
                component.setCode(interviewComponent.getCode());
                component.setName(interviewComponent.getName());
                component.setRemarks(interviewComponent.getRemarks());
                component.setStatus(interviewComponent.getStatus());
                component.setComponentOrder(interviewComponent.getComponentOrder());
                component.setComponentType(interviewComponent.getComponentType());

                if (interviewComponent.getStatementImage() != null) {
                    component.setStatement(statementImageMapper.convert(interviewComponent.getStatementImage()));
                }

                if (interviewComponent.getQuestion() != null) {
                    Question question = interviewComponent.getQuestion();
                    ContentQuestionDTO contentQuestion = new ContentQuestionDTO();
                    contentQuestion.setId(question.getId());
                    contentQuestion.setCode(question.getCode());
                    contentQuestion.setName(question.getName());
                    contentQuestion.setRemarks(question.getRemarks());
                    contentQuestion.setStatus(question.getStatus());
                    contentQuestion.setQuestionType(question.getQuestionType());

                    List<BaseResponseDTO> questionAnswers = new ArrayList<>();
                    for (QuestionAnswer questionAnswer : questionAnswerService.findByQuestionIdOrderByName(question.getId())) {
                        questionAnswers.add(questionAnswerMapper.convertBasic(questionAnswer));
                    }
                    contentQuestion.setQuestionAnswers(questionAnswers);

                    component.setQuestion(contentQuestion);
                }
                components.add(component);
            }
        }else {
            LOGGER.error("Interview Event Is Not Found");
        }
        return components;
    }

    @Override
    public ResponseData mergeComponentCompletely(RequestInsertComponentDTO p_RequestInsertComponentDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        if (p_RequestInsertComponentDTO.getEventId() != null) {
            InterviewEvent event = interviewEventService.findById(p_RequestInsertComponentDTO.getEventId());
            if (event != null) {
                for (ContentInsertComponentDTO componentDTO : p_RequestInsertComponentDTO.getComponent()) {
                    InterviewComponent interviewComponent;
                    if (componentDTO.getComponentId().equals(0L)) {
                        interviewComponent = new InterviewComponent();
                        interviewComponent.setComponentOrder(componentDTO.getComponentOrder());
                        interviewComponent.setComponentType(componentDTO.getComponentType());
                        interviewComponent.setComponentOrder(componentDTO.getComponentOrder());
                        interviewComponent.setInterviewEvent(event);
                        insert(interviewComponent);
                    }else {
                        LOGGER.error("Component ID is Not Null, Process Updating Component {}", componentDTO.getComponentId());
                        interviewComponent = findById(componentDTO.getComponentId());
                        if (interviewComponent != null) {
                            interviewComponent.setComponentOrder(componentDTO.getComponentOrder());
                            interviewComponent.setComponentType(componentDTO.getComponentType());
                            interviewComponent.setComponentOrder(componentDTO.getComponentOrder());
                            interviewComponent.setInterviewEvent(event);
                            update(interviewComponent);
                        }else {
                            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                            LOGGER.error("Failed Updating Component");
                        }
                    }
                    if (interviewComponent != null) {
                        if (interviewComponent.getId() != null) {
                            Question question = null;
                            if (componentDTO.getQuestions() != null && componentDTO.getQuestions().getQuestionId().equals(0L)) {
                                LOGGER.info("INSERTING Question");
                                question = new Question();

                            } else if (componentDTO.getQuestions() != null && !componentDTO.getQuestions().getQuestionId().equals(0L)) {
                                LOGGER.info("Updating Question {}", componentDTO.getQuestions().getQuestionId());
                                question = questionService.findById(componentDTO.getQuestions().getQuestionId());
                            }
                            if (question != null) {
                                question.setInterviewComponent(interviewComponent);
                                question.setName(componentDTO.getQuestions().getName());
                                questionService.insert(question);
                                if (componentDTO.getQuestions().getQuestionAnswers() != null) {
                                    if (componentDTO.getQuestions().getQuestionAnswers().size() > 0) {
                                        question.setQuestionType(LsiCoreConstant.QuestionType.MULTIPLE_CHOICE);
                                        LOGGER.info("Question Has Multiple Answer Choice (MULTIPLE CHOICE)");
                                        QuestionAnswer questionAnswer;
                                        for (ContentInsertQuestionAnswerDTO questionAnswerDTO : componentDTO.getQuestions().getQuestionAnswers()) {
                                            if (questionAnswerDTO.getQuestionAnswerId().equals(0L)) {
                                                questionAnswer = new QuestionAnswer();
                                            }else {
                                                questionAnswer = questionAnswerService.findById(questionAnswerDTO.getQuestionAnswerId());
                                            }
                                            if (questionAnswer != null) {
                                                questionAnswer.setQuestion(question);
                                                questionAnswer.setName(questionAnswerDTO.getName());
                                                questionAnswerService.insert(questionAnswer);
                                            }else {
                                                LOGGER.error("Question Answer is Not Found");
                                            }
                                        }
                                    }else {
                                        question.setQuestionType(LsiCoreConstant.QuestionType.ESSAY);
                                        LOGGER.info("Question Has No Answer Choice (ESSAY)");
                                    }
                                }else {
                                    question.setQuestionType(LsiCoreConstant.QuestionType.ESSAY);
                                }
                                questionService.update(question);
                            }else {
                                LOGGER.error("Question is Not Found");
                            }

                            StatementImage statementImage = null;
                            if (componentDTO.getStatement() != null && componentDTO.getStatement().getStatementId().equals(0L)) {
                                LOGGER.info("Inserting Statement");
                                statementImage = new StatementImage();
                            } else if (componentDTO.getStatement() != null && !componentDTO.getStatement().getStatementId().equals(0L)) {
                                LOGGER.info("Updating Statement {}", componentDTO.getStatement().getStatementId());
                                statementImage = statementService.findById(componentDTO.getStatement().getStatementId());
                            }
                            if (statementImage != null) {
                                statementImage.setInterviewComponent(interviewComponent);
                                statementImage.setName(componentDTO.getStatement().getName());
                                statementService.insert(statementImage);
                            }else {
                                LOGGER.error("Statement is Not Found");
                            }
                        }else {
                            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                            LOGGER.error("Failed Inserting Component, Operation Will Be Break");
                        }
                    }else {
                        LOGGER.error("Interview Component is Not Found");
                    }
                }
            }else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                LOGGER.error("Event is Not Found");
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            LOGGER.error("Event Id Can Not Be Null");
        }
        return responseData;
    }
}
