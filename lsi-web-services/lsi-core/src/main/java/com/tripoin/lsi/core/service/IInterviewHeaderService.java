package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.*;
import com.tripoin.lsi.core.data.dto.response.*;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewHeaderService extends IScaffoldingService<InterviewHeader, RequestInterviewHeaderDTO> {

    Page<ContentInterviewProgressDTO> findInterviewProgressByStatus(String p_Bearer, String p_Principal, RequestInterviewProgressByStatusDTO p_RequestInterviewProgressByStatusDTO, PageRequest p_PageRequest) throws ServiceException;

    ResponseDetailInterviewDTO findInterviewDetailByRespondentName(String p_Bearer, String p_RespondentName) throws ServiceException;

    List<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader) throws ServiceException;

    Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException;

    Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId, PageRequest p_PageRequest) throws ServiceException;

    List<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId) throws ServiceException;

    Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserIdPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest, List<Long> userId) throws ServiceException;

    List<InterviewHeader> findByVolunteerId(Long p_VolunteerId) throws ServiceException;

    List<InterviewHeader> findInterviewHeaderByArea(RequestFilterInterviewHeader p_RequestFilterInterviewHeader) throws ServiceException;

    Page<InterviewHeader> findInterviewHeaderByAreaAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, List<Long> p_UserId, PageRequest p_PageRequest) throws ServiceException;

    List<InterviewHeader> findInterviewHeaderByAreaAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId) throws ServiceException;

    Page<InterviewHeader> findInterviewHeaderByAreaAndUserIdPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId, PageRequest p_PageRequest) throws ServiceException;

    Page<ContentVolunteerProgressDTO> findInterviewProgressByVolunteer(String p_Bearer, String p_Principal, RequestInterviewProgressByVolunteerDTO p_RequestInterviewProgressByVolunteerDTO, PageRequest p_PageRequest) throws ServiceException;

    Page<ContentAreaProgressDTO> findInterviewProgressByArea(String p_Bearer, String p_Principal, RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO, PageRequest p_PageRequest) throws ServiceException;

    List<Long> findVillageIdByUserId(Long p_UserId) throws ServiceException;

    ResponseData assignAgent(String p_Bearer, Long eventId, Long p_BranchId, Long p_UserId, Map<Long, Integer> mapVillageRespondent) throws ServiceException;

    Integer countTotalNumberOfRespondentByUserId(Long p_UserId) throws ServiceException;

    List<Long> findDistinctEventIdByUserId(Long p_UserId) throws ServiceException;

    List<InterviewHeader> findByEventId(Long p_EventId) throws ServiceException;

    InterviewHeader findByEventIdAndUserIdAndVillageId(Long p_EventId, Long p_UserId, Long p_VillageId) throws ServiceException;

    List<LOVBaseResponseDTO> findProvinceLOVByBranch(String p_Bearer, Long p_BranchId) throws ServiceException;
}
