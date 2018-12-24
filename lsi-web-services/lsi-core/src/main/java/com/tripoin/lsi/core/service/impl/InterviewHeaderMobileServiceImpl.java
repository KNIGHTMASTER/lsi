package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.EMobileResponseCode;
import com.tripoin.lsi.core.LsiCoreConstant;
import com.tripoin.lsi.core.client.impl.RoleClientImpl;
import com.tripoin.lsi.core.client.impl.UserClientImpl;
import com.tripoin.lsi.core.client.impl.VillageClientImpl;
import com.tripoin.lsi.core.data.dto.request.RequestFilterInterviewHeader;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringIncludeAgentDTO;
import com.tripoin.lsi.core.data.dto.response.ContentAssignmentAgentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseCheckAuthority;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewComponentDetailDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ContentMobileEventCandidate;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileFindDetailAssignmentByAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileInterviewComponent;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobilePointFilteringDTO;
import com.tripoin.lsi.core.data.mapper.EventCandidateMapper;
import com.tripoin.lsi.core.data.mapper.InterviewComponentMapper;
import com.tripoin.lsi.core.data.model.EventCandidate;
import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.service.*;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.CommonConstant;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewHeaderMobileServiceImpl implements IInterviewHeaderMobileService {

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private IInterviewDetailService interviewDetailService;

    @Autowired
    private IInterviewComponentService interviewComponentService;

    @Autowired
    private EventCandidateMapper eventCandidateMapper;

    @Autowired
    private InterviewComponentMapper interviewComponentMapper;

    @Autowired
    private IEventCandidateService eventCandidateService;

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private RoleClientImpl roleClient;

    @Autowired
    private VillageClientImpl villageClient;

    @Autowired
    private IParameterService parameterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewHeaderMobileServiceImpl.class);

    @Override
    public ResponseMobileFindDetailAssignmentByAgentDTO findDetailAssignment(String p_Bearer, Principal p_Principal) throws ServiceException {
        ResponseMobileFindDetailAssignmentByAgentDTO response = new ResponseMobileFindDetailAssignmentByAgentDTO();
        ResponseCheckAuthority responseCheckAuthority = checkAuthority(p_Bearer, p_Principal, ScaffoldingSecurityConstant.RoleCode.VOLUNTEER);
        response.setResponseData(responseCheckAuthority.getResponseData());
        if (response.getResponseData().getResponseCode().equals(EMobileResponseCode.RC_ACCESS_GRANTED.getResponseCode())) {
            response.setName(responseCheckAuthority.getUser().getName());
            List<InterviewHeader> interviewHeaders = interviewHeaderService.findByVolunteerId(responseCheckAuthority.getUser().getId());
            RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
            List<ContentAssignmentAgentDTO> agentAssignments = new ArrayList<>();
            Integer nCompletedInterview = 0;
            Integer nTotalPoint = 0;
            for (InterviewHeader interviewHeader : interviewHeaders) {
                if (interviewHeader.getVillageId() != null) {
                    requestFindById.setId(interviewHeader.getVillageId());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> responseVillage = villageClient.findVillageById(p_Bearer, requestFindById);
                    if (responseVillage != null) {
                        if (responseVillage.getContent() != null) {
                            ContentAssignmentAgentDTO contentAssignmentAgent = new ContentAssignmentAgentDTO();
                            contentAssignmentAgent.setInterviewHeaderId(interviewHeader.getId());
                            contentAssignmentAgent.setVillageId(responseVillage.getContent().getId());
                            contentAssignmentAgent.setVillageName(responseVillage.getContent().getName());
                            contentAssignmentAgent.setLatitude(responseVillage.getContent().getLatitude());
                            contentAssignmentAgent.setLongitude(responseVillage.getContent().getLongitude());
                            contentAssignmentAgent.setNumberOfRespondent(interviewHeader.getNumberOfRespondent());
                            contentAssignmentAgent.setRadiusTolerance(responseVillage.getContent().getRadiusTolerance());
                            if (responseVillage.getContent().getDistrict() != null) {
                                contentAssignmentAgent.setDistrictId(responseVillage.getContent().getDistrict().getId());
                                contentAssignmentAgent.setDistrictName(responseVillage.getContent().getDistrict().getName());
                                if (responseVillage.getContent().getDistrict().getCity() != null) {
                                    contentAssignmentAgent.setCityId(responseVillage.getContent().getDistrict().getCity().getId());
                                    contentAssignmentAgent.setCityName(responseVillage.getContent().getDistrict().getCity().getName());
                                    if (responseVillage.getContent().getDistrict().getCity().getProvince() != null) {
                                        contentAssignmentAgent.setProvinceId(responseVillage.getContent().getDistrict().getCity().getProvince().getId());
                                        contentAssignmentAgent.setProvinceName(responseVillage.getContent().getDistrict().getCity().getProvince().getName());
                                    }
                                }
                            }
                            agentAssignments.add(contentAssignmentAgent);
                        }
                    }
                    Integer nSingleCompletedInterview = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(interviewHeader.getId(), LsiCoreConstant.InterviewStatus.COMPLETED);
                    Integer nSingleTotalPoint = interviewDetailService.countDataByPointGreaterThanZeroByInterviewHeaderId(interviewHeader.getId());
                    if (nSingleCompletedInterview == null) {
                        nSingleCompletedInterview = 0;
                    }
                    if (nSingleTotalPoint == null) {
                        nSingleTotalPoint = 0;
                    }
                    nCompletedInterview += nSingleCompletedInterview;
                    nTotalPoint += nSingleTotalPoint;
                }
            }
            response.setAgentAssignments(agentAssignments);
            Integer totalNRespondent = interviewHeaderService.countTotalNumberOfRespondentByUserId(responseCheckAuthority.getUser().getId());
            if (totalNRespondent != null) {
                response.setTotalAssignedInterview(totalNRespondent);
            }
            response.setTotalCompletedInterview(nCompletedInterview);
            response.setTotalPoint(nTotalPoint);
        } else {
            LOGGER.error("Access Denied");
        }
        return response;
    }

    @Override
    public ResponseCheckAuthority checkAuthority(String p_Bearer, Principal p_Principal, String expectedAuthority) throws ServiceException {
        ResponseCheckAuthority responseCheckAuthority = new ResponseCheckAuthority();
        ResponseData responseData;
        if (p_Principal.getName() != null) {
            GenericSingleDATAResponseDTO<ResponseUserDTO> responseUserDTO = userClient.findByUserName(p_Bearer, p_Principal.getName());
            if (responseUserDTO.getContent() != null) {
                responseCheckAuthority.setUser(responseUserDTO.getContent());
                RequestFindByNameDTO requestFindByNameDTO = new RequestFindByNameDTO();
                requestFindByNameDTO.setName(p_Principal.getName());
                GenericListResponseDTO<ResponseRoleDTO> responseRoleDTO = roleClient.findRolesByUserName(p_Bearer, requestFindByNameDTO);
                if (responseRoleDTO.getContent().size() > 0) {
                    ResponseRoleDTO role = responseRoleDTO.getContent().get(0);
                    responseCheckAuthority.setRole(role);
                    if (role.getCode().equals(expectedAuthority)) {
                        responseData = new ResponseData(EMobileResponseCode.RC_ACCESS_GRANTED.getResponseCode(), EMobileResponseCode.RC_ACCESS_GRANTED.getResponseMsg());
                    }else {
                        responseData = new ResponseData(EMobileResponseCode.RC_ACCESS_DENIED.getResponseCode(), EMobileResponseCode.RC_ACCESS_DENIED.getResponseMsg());
                        LOGGER.error("User Not Found");
                    }
                }else {
                    responseData = new ResponseData(EMobileResponseCode.RC_UNAUTHORIZED.getResponseCode(), EMobileResponseCode.RC_UNAUTHORIZED.getResponseMsg());
                    LOGGER.error("User Not Found");
                }
            }else {
                responseData = new ResponseData(EMobileResponseCode.RC_USER_NOT_EXISTS.getResponseCode(), EMobileResponseCode.RC_USER_NOT_EXISTS.getResponseMsg());
                LOGGER.error("User Not Found");
            }
        }else {
            responseData = new ResponseData(EMobileResponseCode.RC_BAD_CREDENTIALS.getResponseCode(), EMobileResponseCode.RC_BAD_CREDENTIALS.getResponseMsg());
            LOGGER.error("Principal Not Found");
        }
        responseCheckAuthority.setResponseData(responseData);
        return responseCheckAuthority;
    }

    @Override
    public ResponseMobileInterviewComponent findInterviewComponentByPrincipal(String p_Bearer, Principal p_Principal) throws ServiceException {
        ResponseMobileInterviewComponent response = new ResponseMobileInterviewComponent();
        ResponseCheckAuthority volunteerAuthority = checkAuthority(p_Bearer, p_Principal, ScaffoldingSecurityConstant.RoleCode.VOLUNTEER);
        if (volunteerAuthority != null) {
            response.setResponseData(volunteerAuthority.getResponseData());
            if (volunteerAuthority.getResponseData().getResponseCode().equals(EMobileResponseCode.RC_ACCESS_GRANTED.getResponseCode())) {
                List<Long> eventIds = interviewHeaderService.findDistinctEventIdByUserId(volunteerAuthority.getUser().getId());
                for (Long eventId : eventIds) {
                    List<ContentMobileEventCandidate> eventCandidateDTOs = new ArrayList<>();
                    for (EventCandidate eventCandidate : eventCandidateService.findByInterviewEventId(eventId)) {
                        if (eventCandidate.getInterviewEvent() != null) {
                            response.setStartTimeStamp(FormatDateConstant.DEFAULT.format(eventCandidate.getInterviewEvent().getEventStartDate()));
                            response.setEndTimeStamp(FormatDateConstant.DEFAULT.format(eventCandidate.getInterviewEvent().getEventEndDate()));
                            if (eventCandidate.getInterviewEvent().getIsVote() != null) {
                                if (eventCandidate.getInterviewEvent().getIsVote() == CommonConstant.GeneralValue.ONE) {
                                    if (eventCandidate.getCandidateImagePath() != null) {
                                        eventCandidate.setCandidateImagePath(parameterService.getEventImagePath().concat(eventCandidate.getCandidateImagePath()));
                                    }
                                    eventCandidateDTOs.add(eventCandidateMapper.convertOnlyContent(eventCandidate));
                                }else {
                                    LOGGER.info("Vote is Null");
                                }
                            }else {
                                LOGGER.warn("Event {} Has No Vote Feature", eventCandidate.getInterviewEvent().getName());
                            }
                        }else {
                            LOGGER.warn("Event {} Not Found", eventId);
                        }
                    }
                    response.setEventCandidatesBefore(eventCandidateDTOs);
                    response.setEventCandidatesAfter(eventCandidateDTOs);

                    List<ResponseInterviewComponentDetailDTO> components  = new ArrayList<>();
                    for (InterviewComponent component : interviewComponentService.findByInterviewEventIdOrderByComponentOrder(eventId)) {
                        components.add(interviewComponentMapper.convertDetail(component));
                    }
                    response.setInterviewComponents(components);
                }
                response.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
            }else {
                LOGGER.error("Access Denied");
            }
        }else {
            LOGGER.error("Volunteer User Not Found");
        }
        return response;
    }

    @Override
    public ResponseMobilePointFilteringDTO findPointFiltering(String p_Bearer, Principal p_Principal, RequestMobilePointFilteringDTO p_RequestMobilePointFilteringDTO) throws ServiceException {
        ResponseMobilePointFilteringDTO response = new ResponseMobilePointFilteringDTO();
        response.setResponseData(new ResponseData(EMobileResponseCode.RC_SUCCESS.getResponseCode(), EMobileResponseCode.RC_SUCCESS.getResponseMsg()));
        ResponseCheckAuthority volunteerAuthority = checkAuthority(p_Bearer, p_Principal, ScaffoldingSecurityConstant.RoleCode.VOLUNTEER);
        if (volunteerAuthority.getResponseData().getResponseCode().equals(EMobileResponseCode.RC_ACCESS_GRANTED.getResponseCode())) {
//            RequestFilterInterviewHeader requestFilterInterviewHeader = new RequestFilterInterviewHeader();
//            requestFilterInterviewHeader.setProvinceId(p_RequestMobilePointFilteringDTO.getProvinceId());
//            requestFilterInterviewHeader.setCityId(p_RequestMobilePointFilteringDTO.getCityId());
//            requestFilterInterviewHeader.setDistrictId(p_RequestMobilePointFilteringDTO.getDistrictId());
//            requestFilterInterviewHeader.setVillageId(p_RequestMobilePointFilteringDTO.getVillageId());
//            List<InterviewHeader> interviewHeaders = interviewHeaderService.findInterviewHeaderByArea(requestFilterInterviewHeader);
            List<InterviewHeader> interviewHeaders = interviewHeaderService.findByVolunteerId(volunteerAuthority.getUser().getId());
            LOGGER.info("Interview Header By Area {}", interviewHeaders.size());
            List<InterviewHeader> filteredInterviewHeader = new ArrayList<>();
            for (InterviewHeader interviewHeader : interviewHeaders) {
                if (interviewHeader.getUserId().equals(volunteerAuthority.getUser().getId())) {
                    filteredInterviewHeader.add(interviewHeader);
                }
            }
            LOGGER.info("Interview Header By Area And User Id {}", interviewHeaders.size());

            int nCompletedInterview = 0;
            int nTotalPoint = 0;
            for (InterviewHeader interviewHeader : filteredInterviewHeader) {
                Integer nSingleCompletedInterview = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(interviewHeader.getId(), LsiCoreConstant.InterviewStatus.COMPLETED);
                Integer nSingleTotalPoint = interviewDetailService.countDataByPointGreaterThanZeroByInterviewHeaderId(interviewHeader.getId());

                if (nSingleCompletedInterview == null) {
                    nSingleCompletedInterview = 0;
                }
                if (nSingleTotalPoint == null) {
                    nSingleTotalPoint = 0;
                }
                nCompletedInterview += nSingleCompletedInterview;
                nTotalPoint += nSingleTotalPoint;
            }
            Integer totalNRespondent = interviewHeaderService.countTotalNumberOfRespondentByUserId(volunteerAuthority.getUser().getId());
            if (totalNRespondent != null) {
                response.setTotalAssignedInterview(totalNRespondent);
            }
            response.setTotalPoint(nTotalPoint);
            response.setTotalCompletedInterview(nCompletedInterview);
        }else {
            response.setResponseData(new ResponseData(EMobileResponseCode.RC_ACCESS_DENIED.getResponseCode(),EMobileResponseCode.RC_ACCESS_DENIED.getResponseMsg()));
            LOGGER.error("Access Denied");
        }
        return response;
    }

    @Override
    public ResponseMobilePointFilteringDTO findPointFilteringIncludeAgent(String p_Bearer, Principal p_Principal, RequestMobilePointFilteringIncludeAgentDTO p_RequestMobilePointFilteringIncludeAgentDTO) throws ServiceException {
        ResponseMobilePointFilteringDTO response = new ResponseMobilePointFilteringDTO();
        ResponseCheckAuthority reviewerAuthority = checkAuthority(p_Bearer, p_Principal, ScaffoldingSecurityConstant.RoleCode.SPV_PROVINSI);
        if (reviewerAuthority.getResponseData().getResponseCode().equals(EMobileResponseCode.RC_ACCESS_GRANTED.getResponseCode())) {
            RequestFilterInterviewHeader requestFilterInterviewHeader = new RequestFilterInterviewHeader();
            requestFilterInterviewHeader.setProvinceId(p_RequestMobilePointFilteringIncludeAgentDTO.getProvinceId());
            requestFilterInterviewHeader.setCityId(p_RequestMobilePointFilteringIncludeAgentDTO.getCityId());
            requestFilterInterviewHeader.setDistrictId(p_RequestMobilePointFilteringIncludeAgentDTO.getDistrictId());
            requestFilterInterviewHeader.setVillageId(p_RequestMobilePointFilteringIncludeAgentDTO.getVillageId());
            List<InterviewHeader> interviewHeaders = interviewHeaderService.findInterviewHeaderByArea(requestFilterInterviewHeader);
            LOGGER.info("Interview Header By Area {}", interviewHeaders.size());
            CollectionUtils.filter(interviewHeaders, new Predicate<InterviewHeader>() {
                @Override
                public boolean evaluate(InterviewHeader interviewHeader) {
                    return interviewHeader.getUserId().equals(p_RequestMobilePointFilteringIncludeAgentDTO.getAgentId());
                }
            });
            LOGGER.info("Interview Header By Area And User Id{}", interviewHeaders.size());

            int nCompletedInterview = 0;
            int nTotalPoint = 0;
            for (InterviewHeader interviewHeader : interviewHeaders) {
                nCompletedInterview += interviewDetailService.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(interviewHeader.getId(), LsiCoreConstant.InterviewStatus.COMPLETED);
                nTotalPoint += interviewDetailService.countTotalPointByInterviewHeaderId(interviewHeader.getId());
            }
            Integer totalNRespondent = interviewHeaderService.countTotalNumberOfRespondentByUserId(p_RequestMobilePointFilteringIncludeAgentDTO.getAgentId());
            if (totalNRespondent != null) {
                response.setTotalAssignedInterview(totalNRespondent);
            }
            response.setTotalPoint(nTotalPoint);
            response.setTotalCompletedInterview(nCompletedInterview);
        }else {
            LOGGER.error("Access Denied");
        }
        return null;
    }
}
