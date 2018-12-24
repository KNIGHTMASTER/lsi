package com.tripoin.lsi.core.data.mapper;

import com.tripoin.lsi.core.data.dto.response.ContentUserAnswerBasicDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailByHeaderDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.tripoin.lsi.core.data.model.*;
import com.tripoin.lsi.core.service.*;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import com.wissensalt.util.common.time.FormatDateConstant;
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
public class InterviewDetailMapper extends ADATAMapper<InterviewDetail, ResponseInterviewDetailDTO> {

    @Autowired
    private InterviewHeaderMapper interviewHeaderMapper;

    @Autowired
    private IEventCandidateService eventCandidateService;

    @Autowired
    private EventCandidateMapper eventCandidateMapper;

    @Autowired
    private IInterviewUserAnswerService interviewUserAnswerService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuestionAnswerService questionAnswerService;

    @Autowired
    private IParameterService parameterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewDetailMapper.class);

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
        response.setRespondentAddress(p_InterviewDetail.getRespondentAddress());
        response.setRespondentPhoneNumber(p_InterviewDetail.getRespondentPhoneNumber());
        response.setInterviewScore(p_InterviewDetail.getInterviewScore());
        if (p_InterviewDetail.getInterviewHeader() != null) {
            response.setInterviewHeader(interviewHeaderMapper.convert(p_InterviewDetail.getInterviewHeader()));
        }
        if (p_InterviewDetail.getEventCandidateBefore() != null) {
            try {
                EventCandidate eventCandidate = eventCandidateService.findById(p_InterviewDetail.getEventCandidateBefore());
                response.setEventCandidateBefore(eventCandidateMapper.convert(eventCandidate));
            } catch (ServiceException e) {
                LOGGER.error("Error Find Event Candidate Before {}", e.toString());
            }
        }
        if (p_InterviewDetail.getEventCandidateAfter() != null) {
            try {
                EventCandidate eventCandidate = eventCandidateService.findById(p_InterviewDetail.getEventCandidateAfter());
                response.setEventCandidateAfter(eventCandidateMapper.convert(eventCandidate));
            } catch (ServiceException e) {
                LOGGER.error("Error Find Event Candidate After {}", e.toString());
            }
        }
        response.setValidityStatus(p_InterviewDetail.getValidityStatus());
        List<String> interviewImages = new ArrayList<>();
        if (p_InterviewDetail.getInterviewImages().size() > 0) {
            for (InterviewImage interviewImage : p_InterviewDetail.getInterviewImages()) {
                interviewImages.add(interviewImage.getFilePath());
            }
        }
        response.setInterviewImages(interviewImages);
        return response;
    }

    public ResponseInterviewDetailByHeaderDTO convertDetailByHeader(InterviewDetail p_InterviewDetail) {
        ResponseInterviewDetailByHeaderDTO response = new ResponseInterviewDetailByHeaderDTO();
        response.setId(p_InterviewDetail.getId());
        response.setCode(p_InterviewDetail.getCode());
        response.setName(p_InterviewDetail.getName());
        response.setRemarks(p_InterviewDetail.getRemarks());
        response.setStatus(p_InterviewDetail.getStatus());
        response.setLatitude(p_InterviewDetail.getLatitude());
        response.setLongitude(p_InterviewDetail.getLongitude());
        if (p_InterviewDetail.getInterviewStartTime() != null) {
            response.setInterviewStartDate(FormatDateConstant.DEFAULT.format(p_InterviewDetail.getInterviewStartTime()));
        }
        if (p_InterviewDetail.getInterviewEndTime() != null) {
            response.setInterviewEndDate(FormatDateConstant.DEFAULT.format(p_InterviewDetail.getInterviewEndTime()));
        }
        response.setInterviewStatus(p_InterviewDetail.getInterviewStatus());
        response.setRespondentName(p_InterviewDetail.getRespondentName());
        response.setRespondentAge(p_InterviewDetail.getRespondentAge());
        response.setRespondentReligion(p_InterviewDetail.getRespondentReligion());
        response.setRespondentAddress(p_InterviewDetail.getRespondentAddress());
        response.setRespondentPhoneNumber(p_InterviewDetail.getRespondentPhoneNumber());
        response.setInterviewScore(p_InterviewDetail.getInterviewScore());
        if (p_InterviewDetail.getInterviewHeader() != null) {
            response.setInterviewHeader(interviewHeaderMapper.convert(p_InterviewDetail.getInterviewHeader()));
        }
        if (p_InterviewDetail.getEventCandidateBefore() != null) {
            try {
                EventCandidate eventCandidate = eventCandidateService.findById(p_InterviewDetail.getEventCandidateBefore());
                if (eventCandidate != null) {
                    response.setEventCandidateBefore(eventCandidate.getName());
                }
            } catch (ServiceException e) {
                LOGGER.error("Error Find Event Candidate Before {}", e.toString());
            }
        }
        if (p_InterviewDetail.getEventCandidateAfter() != null) {
            try {
                EventCandidate eventCandidate = eventCandidateService.findById(p_InterviewDetail.getEventCandidateAfter());
                if (eventCandidate != null) {
                    response.setEventCandidateAfter(eventCandidate.getName());
                }
            } catch (ServiceException e) {
                LOGGER.error("Error Find Event Candidate After {}", e.toString());
            }
        }
        response.setValidityStatus(p_InterviewDetail.getValidityStatus());
        List<String> interviewImages = new ArrayList<>();
        String nodeImagePath = "";
        try {
            nodeImagePath = parameterService.getNodeImagePath();
        } catch (ServiceException e) {
            LOGGER.error("Error Get Interview Image Path {}", e.toString());
        }
        if (p_InterviewDetail.getInterviewImages().size() > 0) {
            for (InterviewImage interviewImage : p_InterviewDetail.getInterviewImages()) {
                if (interviewImage.getName() != null) {
                    interviewImages.add(nodeImagePath.concat(interviewImage.getName()));
                }
            }
        }
        response.setInterviewImages(interviewImages);
        List<InterviewUserAnswer> userAnswers = new ArrayList<>();
        try {
            userAnswers = interviewUserAnswerService.findByDetailId(p_InterviewDetail.getId());
        } catch (ServiceException e) {
            LOGGER.error("User Answer Error {}", e.toString());
        }

        List<ContentUserAnswerBasicDTO> questionAnswerDTOs = new ArrayList<>();
        for (InterviewUserAnswer userAnswer : userAnswers) {
            ContentUserAnswerBasicDTO userAnswerBasicDTO = new ContentUserAnswerBasicDTO();
            Question question = null;
            try {
                question = questionService.findById(userAnswer.getInterviewQuestionId());
            } catch (ServiceException e) {
                LOGGER.error("Error Find Question By Id {}", e.toString());
            }
            if (question != null) {
                userAnswerBasicDTO.setQuestion(question.getName());
            }

            if (userAnswer.getQuestionAnswerId() != null) {
                QuestionAnswer questionAnswer = null;
                try {
                    questionAnswer = questionAnswerService.findById(userAnswer.getQuestionAnswerId());
                } catch (ServiceException e) {
                    LOGGER.error("Error Find Answer By Id {}", e.toString());
                }
                if (questionAnswer != null) {
                    userAnswerBasicDTO.setAnswer(questionAnswer.getName());
                }
            }else {
                userAnswerBasicDTO.setAnswer(userAnswer.getOtherAnswer());
            }
            questionAnswerDTOs.add(userAnswerBasicDTO);
        }
        response.setQuestionAnswers(questionAnswerDTOs);
        return response;
    }
}
