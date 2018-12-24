package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.LsiThirdPartyConstant;
import com.tripoin.lsi.thirdparty.client.impl.UserClientImpl;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestFilterInterviewHeader;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewDashboardDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ContentDashboardEventCandidateDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseDashboardInterview;
import com.tripoin.lsi.thirdparty.data.model.EventCandidate;
import com.tripoin.lsi.thirdparty.data.model.InterviewDetail;
import com.tripoin.lsi.thirdparty.data.model.InterviewEvent;
import com.tripoin.lsi.thirdparty.data.model.InterviewHeader;
import com.tripoin.lsi.thirdparty.service.*;
import com.tripoin.lsi.thirdparty.util.PrincipalFinder;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant.RoleCode;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private IInterviewDetailService interviewDetailService;

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private PrincipalFinder principalFinder;

    @Autowired
    private IEventCandidateService eventCandidateService;

    @Autowired
    private IInterviewUserAnswerService userAnswerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Override
    public ResponseDashboardInterview getDashboardInterview(String p_Bearer, String p_Principal, RequestInterviewDashboardDTO p_RequestInterviewDashboard) throws ServiceException  {
        ResponseDashboardInterview response = new ResponseDashboardInterview();
        response.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));

        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        if (role != null) {
            RequestFilterInterviewHeader filterHeader = new RequestFilterInterviewHeader();
            filterHeader.setInterviewEventId(p_RequestInterviewDashboard.getInterviewEventId());
            filterHeader.setProvinceId(p_RequestInterviewDashboard.getProvinceId());
            filterHeader.setCityId(p_RequestInterviewDashboard.getCityId());
            filterHeader.setDistrictId(p_RequestInterviewDashboard.getDistrictId());
            filterHeader.setVillageId(p_RequestInterviewDashboard.getVillageId());
            List<InterviewHeader> interviewHeaders = new ArrayList<>();

            if ((!role.getCode().equals(RoleCode.ACTUATOR)) && (!role.getCode().equals(RoleCode.VOLUNTEER) && (!role.getCode().equals(RoleCode.DIREKSI)) && (!role.getCode().equals(RoleCode.ADMIN)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        filterHeader.setBranchId(user.getBranch().getId());
                        interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
                    } else {
                        LOGGER.error("Branch is Not Found");
                    }
                } else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(RoleCode.ADMIN) || role.getCode().equals(RoleCode.DIREKSI) || role.getCode().equals(RoleCode.THIRD_PARTY)) {
                LOGGER.info("Found Admin || Direksi || Third Party Role");
                interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
            } else {
                LOGGER.error("Role is UnAuthorized to Access This Service");
            }

            LOGGER.info("Found {} Interview Headers ", interviewHeaders.size());
            InterviewEvent interviewEvent = null;
            List<InterviewHeader> filteredHeaders = new ArrayList<>();
            if (interviewHeaders.size() > 0) {
                LOGGER.info("- Before - filter volunteer = {}", interviewHeaders.size());
                GenericSingleDATAResponseDTO<ResponseUserDTO> user;
                if (p_RequestInterviewDashboard.getVolunteerId() != null) {
                    LOGGER.info("Filter by Volunteer {}", p_RequestInterviewDashboard.getVolunteerId());
                    RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                    requestFindByIdDTO.setId(p_RequestInterviewDashboard.getVolunteerId());

                    user = userClient.findByUserId(p_Bearer, requestFindByIdDTO);

                    if (interviewHeaders.size() > 0) {
                        if (user != null) {
                            Long userId = user.getContent().getId();
                            for (InterviewHeader interviewHeader : interviewHeaders) {
                                if (interviewHeader.getUserId().equals(userId)) {
                                    filteredHeaders.add(interviewHeader);
                                }
                            }
                        }
                    }
                }else {
                    LOGGER.info("No Filter by Volunteer");
                    if (filteredHeaders.size() <= 0) {
                        filteredHeaders.addAll(interviewHeaders);
                    }
                }
                LOGGER.info("- After - filter volunteer = {}", filteredHeaders.size());

                Integer nTotalCompleteInterview = 0;
                Integer nTotalNotCompleteInterview = 0;
                /*Integer nTotalValidInterview = 0;
                Integer nTotalNotValidInterview = 0;
                Integer nTotalValidEnoughInterview = 0;
                Integer nTotalLessValidInterview = 0;*/
                Map<Long, Integer> eventCandidateBefore =  new HashMap<>();
                Map<Long, Integer> eventCandidateAfter = new HashMap<>();

                Map<String, Map<String, Integer>> mapTotalUserAnswer = new HashMap<>();

                if (filteredHeaders.size() > 0) {
                    interviewEvent = filteredHeaders.get(0).getInterviewEvent();
                }
                for (InterviewHeader interviewHeader : filteredHeaders) {
//                    Integer nInterviewByHeader = interviewDetailService.countInterviewDetailByInterviewHeaderId(interviewHeader.getId());
//                    Integer nValidInterview = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndValidityStatus(
//                            interviewHeader.getId(), LsiCoreConstant.ValidityStatus.VALID
//                    );
//                    Integer nValidEnough = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndValidityStatus(
//                            interviewHeader.getId(), LsiCoreConstant.ValidityStatus.VALID_ENOUGH
//                    );
//                    Integer nLessValid = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndValidityStatus(
//                            interviewHeader.getId(), LsiCoreConstant.ValidityStatus.LESS_VALID
//                    );
//                    Integer nNotValid = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndValidityStatus(
//                            interviewHeader.getId(), LsiCoreConstant.ValidityStatus.NOT_VALID
//                    );
                    if (interviewHeader.getInterviewEvent() != null) {
                        if (interviewHeader.getInterviewEvent().getIsVote() == 1) {
                            for (InterviewDetail interviewDetail : interviewHeader.getInterviewDetails()) {
                                Integer valueBefore = eventCandidateBefore.get(interviewDetail.getEventCandidateBefore());
                                if (valueBefore == null) {
                                    valueBefore = 1;
                                }else {
                                    valueBefore += 1;
                                }
                                eventCandidateBefore.put(interviewDetail.getEventCandidateBefore(), valueBefore);

                                Integer valueAfter = eventCandidateAfter.get(interviewDetail.getEventCandidateAfter());
                                if (valueAfter == null) {
                                    valueAfter = 1;
                                }else {
                                    valueAfter += 1;
                                }
                                eventCandidateAfter.put(interviewDetail.getEventCandidateAfter(), valueAfter);
                            }
                        }
                    }
                    int nCompleteInterview = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(
                            interviewHeader.getId(), LsiThirdPartyConstant.InterviewStatus.COMPLETED
                    );
//                    if (nNotValid == null) {
//                        nNotValid = 0;
//                    }
//                    if (nLessValid == null) {
//                        nLessValid = 0;
//                    }
//                    if (nValidEnough == null) {
//                        nValidEnough = 0;
//                    }
                    nTotalCompleteInterview += nCompleteInterview;
                    nTotalNotCompleteInterview += (interviewHeader.getNumberOfRespondent() - nCompleteInterview);

//                    nTotalValidInterview += nValidInterview;
//                    nTotalNotValidInterview += nNotValid;
//
//                    nTotalValidEnoughInterview += nValidEnough;
//                    nTotalLessValidInterview += nLessValid;

                    for (InterviewDetail interviewDetail : interviewHeader.getInterviewDetails()) {
                        Map<String, Map<String, Integer>> mapUserAnswer = userAnswerService.findMapsUserAnswerByInterviewDetailId(interviewDetail.getId());
                        for (Map.Entry<String, Map<String, Integer>> mapUA : mapUserAnswer.entrySet()) {
                            if (mapTotalUserAnswer.containsKey(mapUA.getKey())) {
                                for (Map.Entry<String, Integer> mapDetail : mapUA.getValue().entrySet()) {
                                    if (mapTotalUserAnswer.get(mapUA.getKey()).containsKey(mapDetail.getKey())) {
                                        mapTotalUserAnswer.get(mapUA.getKey()).put(mapDetail.getKey(), mapTotalUserAnswer.get(mapUA.getKey()).get(mapDetail.getKey()) + mapDetail.getValue());
                                    }else {
                                        mapTotalUserAnswer.get(mapUA.getKey()).put(mapDetail.getKey(), mapDetail.getValue());
                                    }
                                }
                            }else {
                                mapTotalUserAnswer.put(mapUA.getKey(), mapUA.getValue());
                            }
                        }
                        /*for (Map.Entry<String, Integer> map : mapUserAnswer.entrySet()) {
                            if (mapTotalUserAnswer.containsKey(map.getKey())) {
                                *//*UPDATE*//*
                                mapTotalUserAnswer.put(map.getKey(), (mapTotalUserAnswer.get(map.getKey()) + map.getValue()));
                            }else {
                                *//*INSERT NEW*//*
                                mapTotalUserAnswer.put(map.getKey(), map.getValue());
                            }
                        }*/
                    }
                }
                response.setUserAnswers(mapTotalUserAnswer);
                /*if (nTotalCompleteInterview == null) {
                    nTotalCompleteInterview = 0;
                }
                if (nTotalNotCompleteInterview == null) {
                    nTotalNotCompleteInterview = 0;
                }
                nTotalNotCompleteInterview =  nTotalNotCompleteInterview - nTotalCompleteInterview;*/

                response.setNumberOfCompleteData(nTotalCompleteInterview);
                response.setNumberOfNotCompleteData(nTotalNotCompleteInterview);
                /*response.setNumberOfValidData(nTotalValidInterview);
                response.setNumberOfNotValidData(nTotalNotValidInterview);
                response.setNumberOfLessValidData(nTotalLessValidInterview);
                response.setNumberOfValidEnoughData(nTotalValidEnoughInterview);*/

                List<ContentDashboardEventCandidateDTO> resultCandidateBefore = new ArrayList<>();
                List<ContentDashboardEventCandidateDTO> resultCandidateAfter = new ArrayList<>();


                if (interviewEvent != null) {
                    if (interviewEvent.getIsVote() == 0) {
                        for (Map.Entry<Long, Integer> mapCandidate : eventCandidateBefore.entrySet()) {
                            if (mapCandidate.getKey() != null) {
                                EventCandidate eventCandidate = eventCandidateService.findById(mapCandidate.getKey());
                                if (eventCandidate != null) {
                                    ContentDashboardEventCandidateDTO contentDashboardEventCandidate = new ContentDashboardEventCandidateDTO();
                                    contentDashboardEventCandidate.setId(mapCandidate.getKey());
                                    contentDashboardEventCandidate.setName(eventCandidate.getName());
                                    contentDashboardEventCandidate.setNumber(mapCandidate.getValue());
                                    resultCandidateBefore.add(contentDashboardEventCandidate);
                                }
                            }
                        }
                        for (Map.Entry<Long, Integer> mapCandidate : eventCandidateAfter.entrySet()) {
                            if (mapCandidate.getKey() != null) {
                                EventCandidate eventCandidate = eventCandidateService.findById(mapCandidate.getKey());
                                if (eventCandidate != null) {
                                    ContentDashboardEventCandidateDTO contentDashboardEventCandidate = new ContentDashboardEventCandidateDTO();
                                    contentDashboardEventCandidate.setId(mapCandidate.getKey());
                                    contentDashboardEventCandidate.setName(eventCandidate.getName());
                                    contentDashboardEventCandidate.setNumber(mapCandidate.getValue());
                                    resultCandidateAfter.add(contentDashboardEventCandidate);
                                }
                            }
                        }
                        response.setEventCandidateBefore(resultCandidateBefore);
                        response.setEventCandidateAfter(resultCandidateAfter);
                    }
                }
            }else {
                response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
                LOGGER.error("Interview Header Not Found");
            }
        } else {
            LOGGER.error("Role Is Not Found");
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

}
