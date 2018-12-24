package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestFilterInterviewHeader;
import com.tripoin.lsi.core.data.dto.request.RequestInsertInterviewDetailResultDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewDetailService extends IScaffoldingService<InterviewDetail, RequestInterviewDetailDTO> {

    Page<InterviewDetail> findByInterviewHeaderIdAndValidityStatus(List<Long> interviewHeaders, String p_ValidityStatus, Pageable pageable) throws ServiceException;

    List<InterviewDetail> findByInterviewHeaderIdAndValidityStatus(Long p_InterviewHeaderId, String p_ValidityStatus) throws ServiceException;

    List<InterviewDetail> findByInterviewHeaderIdAndValidityStatusNotValid(Long p_InterviewHeaderId) throws ServiceException;

    Page<InterviewDetail> findByInterviewHeaderIdAndValidityStatusNotValid(List<Long> interviewHeaders, Pageable p_PageRequest) throws ServiceException;

    Page<InterviewDetail> findByInterviewHeaderId(Long p_InterviewHeaderId, Pageable pageable) throws ServiceException;

    List<InterviewDetail> findByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException;

    Integer countInterviewDetailByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException;

    Integer countInterviewDetailByInterviewHeaderIdAndInterviewStatus(Long p_InterviewHeaderId, String p_InterviewStatus) throws ServiceException;

    Integer countRespondentAmountByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException;

    Integer countTotalPointByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException;

    Integer countDataByPointGreaterThanZeroByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException;

    Integer countInterviewDetailByInterviewHeaderIdAndValidityStatus(Long p_InterviewHeaderId, String p_ValidityStatus) throws ServiceException;

    Integer countInterviewDetailByInterviewHeaderIdAndScore(Long p_InterviewHeaderId, Integer p_Score) throws ServiceException;

    InterviewDetail findByRespondentName(String p_RespondentName) throws ServiceException;

    @Transactional
    ResponseData insertInterviewDetailResult(RequestInsertInterviewDetailResultDTO p_RequestInsertInterviewDetailResultDTO) throws ServiceException;

    List<Object[]> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException;

    Page<Object[]> findInterviewHeaderByAreaAndEventAndBranchPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException;
}