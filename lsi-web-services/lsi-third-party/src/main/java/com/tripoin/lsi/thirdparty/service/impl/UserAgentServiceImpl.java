package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.LsiThirdPartyConstant;
import com.tripoin.lsi.thirdparty.client.impl.UserClientImpl;
import com.tripoin.lsi.thirdparty.client.impl.VillageClientImpl;
import com.tripoin.lsi.thirdparty.data.dto.request.*;
import com.tripoin.lsi.thirdparty.data.dto.response.ContentDetailUserAgent;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseDetailUserAgentById;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseUserAgentDTO;
import com.tripoin.lsi.thirdparty.data.mapper.InterviewEventMapper;
import com.tripoin.lsi.thirdparty.data.model.InterviewEvent;
import com.tripoin.lsi.thirdparty.data.model.InterviewHeader;
import com.tripoin.lsi.thirdparty.service.IInterviewEventService;
import com.tripoin.lsi.thirdparty.service.IInterviewHeaderService;
import com.tripoin.lsi.thirdparty.service.IUserAgentService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.*;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class UserAgentServiceImpl implements IUserAgentService {

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private IInterviewEventService interviewEventService;

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private VillageClientImpl villageClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAgentServiceImpl.class);

    @Override
    public Page<ResponseUserAgentDTO> findUserAgents(String p_Bearer, RequestFindUserAgentDTO p_RequestFindUserAgentDTO, PageRequest p_PageRequest) throws ServiceException {
        List<ResponseUserAgentDTO> result = new ArrayList<>();
        RequestFilterInterviewHeader requestFilter = new RequestFilterInterviewHeader();
        requestFilter.setInterviewEventId(p_RequestFindUserAgentDTO.getEventId());
        requestFilter.setProvinceId(p_RequestFindUserAgentDTO.getProvinceId());
        requestFilter.setCityId(p_RequestFindUserAgentDTO.getCityId());
        requestFilter.setDistrictId(p_RequestFindUserAgentDTO.getDistrictId());
        requestFilter.setVillageId(p_RequestFindUserAgentDTO.getVillageId());

        List<ResponseUserDTO> users = new ArrayList<>();
        if (p_RequestFindUserAgentDTO.getUserName() != null && p_RequestFindUserAgentDTO.getName() != null) {
            if (p_RequestFindUserAgentDTO.getUserName().length() > 0 && p_RequestFindUserAgentDTO.getName().length() > 0) {
                LOGGER.info("Filter Agent By UserName & Name");
                RequestFindByCodeNameDTO requestFindByCodeName = new RequestFindByCodeNameDTO();
                requestFindByCodeName.setCode(p_RequestFindUserAgentDTO.getUserName());
                requestFindByCodeName.setName(p_RequestFindUserAgentDTO.getName());
                users = userClient.findVolunteerByCodeContainingAndNameContaining(p_Bearer, requestFindByCodeName);
            }
            if (p_RequestFindUserAgentDTO.getName().length() > 0 && p_RequestFindUserAgentDTO.getUserName().length() <= 0) {
                LOGGER.info("Filter Agent By Name {}", p_RequestFindUserAgentDTO.getName());
                RequestFindByNameDTO requestFindByName = new RequestFindByNameDTO();
                requestFindByName.setName(p_RequestFindUserAgentDTO.getName());
                users = userClient.findVolunteerByNameContaining(p_Bearer, requestFindByName);
            }
            if (p_RequestFindUserAgentDTO.getUserName().length() > 0 && p_RequestFindUserAgentDTO.getName().length() <= 0) {
                LOGGER.info("Filter Agent By User Name");
                RequestFindByCodeDTO requestFindByCode = new RequestFindByCodeDTO();
                requestFindByCode.setCode(p_RequestFindUserAgentDTO.getUserName());
                users = userClient.findVolunteerByCodeContaining(p_Bearer, requestFindByCode);
            }
            if (p_RequestFindUserAgentDTO.getUserName().length() <=0 && p_RequestFindUserAgentDTO.getName().length() <= 0) {
                LOGGER.info("Filter Agent - No Filter");
                users = userClient.findAllVolunteer(p_Bearer);
            }
        } else if(p_RequestFindUserAgentDTO.getUserName() == null && p_RequestFindUserAgentDTO.getName() != null) {
            if (p_RequestFindUserAgentDTO.getName().length() > 0 && p_RequestFindUserAgentDTO.getUserName().length() <= 0) {
                LOGGER.info("Filter Agent By Name {}", p_RequestFindUserAgentDTO.getName());
                RequestFindByNameDTO requestFindByName = new RequestFindByNameDTO();
                requestFindByName.setName(p_RequestFindUserAgentDTO.getName());
                users = userClient.findVolunteerByNameContaining(p_Bearer, requestFindByName);
            }
        } else if (p_RequestFindUserAgentDTO.getUserName() != null && p_RequestFindUserAgentDTO.getName() == null) {
            if (p_RequestFindUserAgentDTO.getUserName().length() > 0 && p_RequestFindUserAgentDTO.getName().length() <= 0) {
                LOGGER.info("Filter Agent By User Name");
                RequestFindByCodeDTO requestFindByCode = new RequestFindByCodeDTO();
                requestFindByCode.setCode(p_RequestFindUserAgentDTO.getUserName());
                users = userClient.findVolunteerByCodeContaining(p_Bearer, requestFindByCode);
            }
        }else if (p_RequestFindUserAgentDTO.getUserName() == null && p_RequestFindUserAgentDTO.getName() == null) {
            LOGGER.info("Filter Agent - No Filter");
            users = userClient.findAllVolunteer(p_Bearer);
        }

        Page<InterviewHeader> interviewHeaders;

        LOGGER.info("Found {} Agent Size", users.size());

        List<Long> userIds = new ArrayList<>();
        for (ResponseUserDTO user: users) {
            userIds.add(user.getId());
        }
        if (requestFilter.getInterviewEventId() != null) {
//                interviewHeaders.addAll(interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranchAndUserId(requestFilter, user.getId(), p_PageRequest).getContent());
//                LOGGER.info("Found {} Interview Headers By Area And Event", interviewHeaders.size());
            interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranchAndUserIdPaged(requestFilter, p_PageRequest, userIds);
        }else {
            interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndUserId(requestFilter, userIds, p_PageRequest);
//                LOGGER.info("Found {} Interview Headers By Area ", interviewHeaders.size());
        }


        String interviewProgress = LsiThirdPartyConstant.InterviewProgress.NOT_STARTED;
        if (interviewHeaders != null) {
            for (InterviewHeader interviewHeader : interviewHeaders) {
                if (interviewHeader.getInterviewDetails() != null) {
                    if (interviewHeader.getInterviewDetails().size() > 0) {
                        interviewProgress = LsiThirdPartyConstant.InterviewProgress.STARTED;
                    }
                }
            }
            for (InterviewHeader interviewHeader : interviewHeaders) {
                ResponseUserAgentDTO userAgentDTO = new ResponseUserAgentDTO();
                if (interviewHeader.getInterviewEvent() != null) {
                    userAgentDTO.setEvent(interviewEventMapper.convert(interviewHeader.getInterviewEvent()));
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                if (interviewHeader.getUserId() != null) {
                    requestFindById.setId(interviewHeader.getUserId());
                    GenericSingleDATAResponseDTO<ResponseUserDTO>  user = userClient.findByUserId(p_Bearer, requestFindById);
                    if (user != null) {
                        if (user.getContent() != null) {
                            userAgentDTO.setUserId(user.getContent().getId());
                            userAgentDTO.setUserName(user.getContent().getCode());
                            userAgentDTO.setName(user.getContent().getName());
                            if (user.getContent().getBranch() != null) {
                                userAgentDTO.setBranch(user.getContent().getBranch());
                            }
                        }
                    }
                    ResponsePhoneNumberEmail phoneNumberEmail = userClient.getPhoneNumberEmail(p_Bearer, requestFindById);
                    if (phoneNumberEmail != null) {
                        if (phoneNumberEmail.getPhoneNumber() != null) {
                            userAgentDTO.setPhoneNumber(phoneNumberEmail.getPhoneNumber());
                        }
                        if (phoneNumberEmail.getEmail() != null) {
                            userAgentDTO.setEmail(phoneNumberEmail.getEmail());
                        }
                    }
                }

                requestFindById.setId(interviewHeader.getVillageId());
                GenericSingleDATAResponseDTO<ResponseVillageDTO> village = villageClient.findVillageById(p_Bearer, requestFindById);
                if (village != null) {
                    if (village.getContent() != null) {
                        ResponseVillageDTO villageResponse = village.getContent();
                        villageResponse.setNumberOfFamilyLeader(interviewHeader.getNumberOfRespondent());
                        userAgentDTO.setVillage(village.getContent());
                    }
                }
                userAgentDTO.setStatus(interviewProgress);
                result.add(userAgentDTO);
            }
        }else {
            LOGGER.error("Header Not Found");
        }
        return new PageImpl<>(result, p_PageRequest, interviewHeaders.getTotalElements());
    }

    @Override
    public ResponseData insertUserAgent(String p_Bearer, RequestInsertAssignUserAgentDTO p_RequestInsertAssignUserAgentDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        if (
                p_RequestInsertAssignUserAgentDTO.getName() != null &&
                        p_RequestInsertAssignUserAgentDTO.getUserName() != null &&
                        p_RequestInsertAssignUserAgentDTO.getPhoneNumber() != null &&
                        p_RequestInsertAssignUserAgentDTO.getPassword() != null &&
                        p_RequestInsertAssignUserAgentDTO.getExpiredDate() != null &&
                        p_RequestInsertAssignUserAgentDTO.getBranchId() != null
                )
        {
            RequestInsertUserAgentDTO requestInsertUserAgent = new RequestInsertUserAgentDTO();
            requestInsertUserAgent.setUserName(p_RequestInsertAssignUserAgentDTO.getUserName());
            requestInsertUserAgent.setName(p_RequestInsertAssignUserAgentDTO.getName());
            requestInsertUserAgent.setPassword(p_RequestInsertAssignUserAgentDTO.getPassword());
            requestInsertUserAgent.setExpiredDate(p_RequestInsertAssignUserAgentDTO.getExpiredDate());
            requestInsertUserAgent.setPhoneNumber(p_RequestInsertAssignUserAgentDTO.getPhoneNumber());
            requestInsertUserAgent.setEmail(p_RequestInsertAssignUserAgentDTO.getEmail());
            requestInsertUserAgent.setBranchId(p_RequestInsertAssignUserAgentDTO.getBranchId());

            /*FILTER BY ACTIVE EVENT ONLY*/
            List<InterviewHeader> interviewHeaders = interviewHeaderService.findByEventId(p_RequestInsertAssignUserAgentDTO.getEventId());
            if (interviewHeaders.size() >0) {
                requestInsertUserAgent.setBranchId(interviewHeaders.get(0).getBranchId());
            }
            ResponseUserDTO responseUserDTO = userClient.insertUserAgent(p_Bearer, requestInsertUserAgent);
            if (responseUserDTO != null) {
                if (
                        p_RequestInsertAssignUserAgentDTO.getEventId() != null &&
                                p_RequestInsertAssignUserAgentDTO.getMapVillageRespondent() != null &&
                                responseUserDTO.getId() != null
                        )
                {
                    if (p_RequestInsertAssignUserAgentDTO.getMapVillageRespondent().size() > 0) {
                        responseData = interviewHeaderService.assignAgent(
                                p_Bearer,
                                p_RequestInsertAssignUserAgentDTO.getEventId(),
                                p_RequestInsertAssignUserAgentDTO.getBranchId(),
                                responseUserDTO.getId(),
                                p_RequestInsertAssignUserAgentDTO.getMapVillageRespondent()
                        );
                    } else {
                        LOGGER.error("Map Village can not be Empty");
                    }
                } else {
                    LOGGER.error("Event Id and Map Village can not be Empty");
                }
            }else {
                LOGGER.error("Error Insert User Agent");
            }
        }else {
            LOGGER.error("Name | User Name | Phone Number can not be null");
        }
        return responseData;
    }

    @Override
    public ResponseDetailUserAgentById findUserAgentById(String p_Bearer, Long p_UserAgentId) throws ServiceException {
        ResponseDetailUserAgentById result = new ResponseDetailUserAgentById();
        List<InterviewHeader> interviewHeaders = interviewHeaderService.findByVolunteerId(p_UserAgentId);
        LOGGER.info("After - found {} Interview Header", interviewHeaders.size());
        RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
        requestFindById.setId(p_UserAgentId);
        String interviewProgress = LsiThirdPartyConstant.InterviewProgress.NOT_STARTED;
        GenericSingleDATAResponseDTO<ResponseUserDTO>  user = userClient.findByUserId(p_Bearer, requestFindById);
        if (user != null) {
            if (user.getContent() != null) {
                result.setUserName(user.getContent().getCode());
                result.setName(user.getContent().getName());
                if (user.getContent().getBranch() != null) {
                    result.setBranch(user.getContent().getBranch());
                }
            }
        }
        ResponsePhoneNumberEmail phoneNumberEmail = userClient.getPhoneNumberEmail(p_Bearer, requestFindById);
        if (phoneNumberEmail != null) {
            if (phoneNumberEmail.getPhoneNumber() != null) {
                result.setPhoneNumber(phoneNumberEmail.getPhoneNumber());
            }
            if (phoneNumberEmail.getEmail() != null) {
                result.setEmail(phoneNumberEmail.getEmail());
            }
        }
        List<ContentDetailUserAgent> detailUserAgents = new ArrayList<>();
        for (InterviewHeader interviewHeader : interviewHeaders) {
            ContentDetailUserAgent contentDetailUserAgent = new ContentDetailUserAgent();
            contentDetailUserAgent.setInterviewHeaderId(interviewHeader.getId());
            if (interviewHeader.getInterviewEvent() != null) {
                contentDetailUserAgent.setEvent(interviewEventMapper.convert(interviewHeader.getInterviewEvent()));
            }
            if (interviewHeader.getInterviewDetails() != null) {
                if (interviewHeader.getInterviewDetails().size() > 0) {
                    interviewProgress = LsiThirdPartyConstant.InterviewProgress.STARTED;
                }
            }
            if (interviewHeader.getVillageId() != null) {
                requestFindById.setId(interviewHeader.getVillageId());
                GenericSingleDATAResponseDTO<ResponseVillageDTO> village = villageClient.findVillageById(p_Bearer, requestFindById);
                if (village != null) {
                    if (village.getContent() != null) {
                        ResponseVillageDTO villageResponse = village.getContent();
                        villageResponse.setNumberOfFamilyLeader(interviewHeader.getNumberOfRespondent());
                        contentDetailUserAgent.setVillage(villageResponse);
                    }
                }
            }
            contentDetailUserAgent.setStatus(interviewProgress);
            detailUserAgents.add(contentDetailUserAgent);
        }
        result.setDetailAssignment(detailUserAgents);
        return result;
    }

    @Override
    public ResponseData assignUserAgent(String p_Bearer, RequestAgentAssignmentDTO p_RequestAgentAssignmentDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        InterviewEvent interviewEvent = interviewEventService.findById(p_RequestAgentAssignmentDTO.getEventId());
        if (interviewEvent != null) {
            if (p_RequestAgentAssignmentDTO.getContentAgentAssignments() != null) {
                if (p_RequestAgentAssignmentDTO.getContentAgentAssignments().size() > 0) {
                    RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                    for (ContentAgentAssignmentDTO agentAssignment : p_RequestAgentAssignmentDTO.getContentAgentAssignments()) {
                        InterviewHeader interviewHeader;
                        if (agentAssignment.getInterviewHeaderId().equals(0L)) {
                            interviewHeader = interviewHeaderService.findByEventIdAndUserIdAndVillageId(p_RequestAgentAssignmentDTO.getEventId(), p_RequestAgentAssignmentDTO.getAgentId(), agentAssignment.getVillageId());
                            if (interviewHeader != null) {
                                LOGGER.error("Can not Assign User in Same Event and Location");
                            }else {
                                interviewHeader = new InterviewHeader();
                            }
                        }else {
                            interviewHeader = interviewHeaderService.findById(agentAssignment.getInterviewHeaderId());
                        }
                        if (interviewHeader != null) {
                            interviewHeader.setUserId(p_RequestAgentAssignmentDTO.getAgentId());
                            interviewHeader.setInterviewEvent(interviewEvent);
                            interviewHeader.setNumberOfRespondent(agentAssignment.getNumberOfRespondent());
                            requestFindById.setId(agentAssignment.getVillageId());
                            GenericSingleDATAResponseDTO<ResponseVillageDTO> villageResponse = villageClient.findVillageById(p_Bearer, requestFindById);
                            if (villageResponse.getContent() != null) {
                                interviewHeader.setVillageId(villageResponse.getContent().getId());
                                if (villageResponse.getContent().getDistrict() != null) {
                                    interviewHeader.setDistrictId(villageResponse.getContent().getDistrict().getId());
                                    if (villageResponse.getContent().getDistrict().getCity() != null) {
                                        interviewHeader.setCityId(villageResponse.getContent().getDistrict().getCity().getId());
                                        if (villageResponse.getContent().getDistrict().getCity().getProvince() != null) {
                                            interviewHeader.setProvinceId(villageResponse.getContent().getDistrict().getCity().getProvince().getId());
                                        }
                                    }
                                }
                            }else {
                                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                                LOGGER.error("Village Not Found");
                            }
                            requestFindById.setId(p_RequestAgentAssignmentDTO.getAgentId());
                            GenericSingleDATAResponseDTO<ResponseUserDTO> userResponse = userClient.findByUserId(p_Bearer, requestFindById);
                            if (userResponse.getContent() != null) {
                                interviewHeader.setBranchId(userResponse.getContent().getBranch().getId());
                                interviewHeaderService.insert(interviewHeader);
                            }else {
                                LOGGER.error("User Not Found, can not set branch");
                                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                            }
                        }else {
                            LOGGER.error("Interview Header Not Found");
                        }
                    }
                }
            }else {
                LOGGER.error("Assignment Can Not Be Empty");
            }
        }else {
            LOGGER.error("Event is Not Found");
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }
}
