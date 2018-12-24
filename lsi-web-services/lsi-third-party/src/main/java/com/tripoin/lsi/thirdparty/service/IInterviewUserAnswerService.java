package com.tripoin.lsi.thirdparty.service;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewUserAnswer;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewUserAnswerService extends IScaffoldingService<InterviewUserAnswer, RequestInterviewUserAnswerDTO> {

    List<InterviewUserAnswer> findByQuestionId(Long p_QuestionId) throws ServiceException;

    InterviewUserAnswer findByQuestionIdAndInterviewDetailId(Long p_QuestionId, Long p_InterviewDetailId) throws ServiceException;

    List<InterviewUserAnswer> findByDetailId(Long p_DetailId) throws ServiceException;

    Map<String, Integer> findMapUserAnswerByInterviewDetailId(Long p_InterviewDetailId) throws ServiceException;

    Map<String, Map<String, Integer>> findMapsUserAnswerByInterviewDetailId(Long p_InterviewDetailId) throws ServiceException;

    Page<ResponseSummaryUserAnswerDTO> findSummaryUserAnswer(String bearer, String p_Principal, RequestSummaryUserAnswerDTO p_RequestSummaryUserAnswerDTO) throws ServiceException;
}
