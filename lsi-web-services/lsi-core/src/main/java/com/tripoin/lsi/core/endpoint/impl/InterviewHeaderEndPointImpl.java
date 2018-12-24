package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewHeaderDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByStatusDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByVolunteerDTO;
import com.tripoin.lsi.core.data.dto.response.*;
import com.tripoin.lsi.core.data.mapper.InterviewHeaderMapper;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.endpoint.IInterviewHeaderEndPoint;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewHeaderEndPointImpl extends AScaffoldingEndPoint<InterviewHeader, RequestInterviewHeaderDTO, ResponseInterviewHeaderDTO> implements IInterviewHeaderEndPoint {

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private InterviewHeaderMapper interviewHeaderMapper;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewHeaderEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = interviewHeaderService;
        dataMapperIntegration = interviewHeaderMapper;
    }

    @RequestLogger(name = "Interview Header|Find Interview Progress By Status")
    @Override
    public Page<ContentInterviewProgressDTO> findInterviewProgressByStatus(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByStatusDTO p_RequestInterviewProgressByStatusDTO) throws EndPointException {
        try {
            if (p_Principal.getName() != null) {
                String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
                PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestInterviewProgressByStatusDTO);
//                List<ContentInterviewProgressDTO> content = interviewHeaderService.findInterviewProgressByStatus(bearer, p_Principal.getName(), p_RequestInterviewProgressByStatusDTO, pageRequest);
                return interviewHeaderService.findInterviewProgressByStatus(bearer, p_Principal.getName(), p_RequestInterviewProgressByStatusDTO, pageRequest);
            }else {
                LOGGER.error("Principal is Not Found");
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Find Interview Progress By Status {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "Interview Header|Get Detail")
    @Override
    public ResponseDetailInterviewDTO findInterviewDetail(HttpServletRequest p_HttpServletRequest, @RequestBody RequestFindByNameDTO requestFindByNameDTO) throws EndPointException {
        ResponseDetailInterviewDTO result = new ResponseDetailInterviewDTO();
        if (requestFindByNameDTO.getName() != null) {
            String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
            try {
                result =  interviewHeaderService.findInterviewDetailByRespondentName(bearer, requestFindByNameDTO.getName());
            } catch (ServiceException e) {
                result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
                LOGGER.error("Error Find Interview Detail {}", e.toString());
            }
        }else {
            result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return result;
    }

    @RequestLogger(name = "volunteer|Find Interview Progress By Volunteer")
    @Override
    public Page<ContentVolunteerProgressDTO> findInterviewProgressByVolunteer(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByVolunteerDTO p_RequestInterviewProgressByVolunteerDTO) throws EndPointException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestInterviewProgressByVolunteerDTO);
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            if (p_Principal.getName() != null) {
                return interviewHeaderService.findInterviewProgressByVolunteer(bearer, p_Principal.getName(), p_RequestInterviewProgressByVolunteerDTO, pageRequest);
            }else {
                LOGGER.error("Principal is Not Found");
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Find Interview Progress By Volunteer {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "volunteer|Find Interview Progress By Area")
    @Override
    public Page<ContentAreaProgressDTO> findInterviewProgressByArea(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO) throws EndPointException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestInterviewProgressByAreaDTO);
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            if (p_Principal.getName() != null) {
                return interviewHeaderService.findInterviewProgressByArea(bearer, p_Principal.getName(), p_RequestInterviewProgressByAreaDTO, pageRequest);
            }else {
                LOGGER.error("Principal is Not Found");
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Find Interview Progress By Area {}", e.toString());
            return null;
        }
    }

    @Override
    public List<LOVBaseResponseDTO> findProvinceLOVByBranch(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody Long branchId) throws EndPointException {
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            if (p_Principal.getName() != null) {
                return interviewHeaderService.findProvinceLOVByBranch(bearer, branchId);
            }else {
                LOGGER.error("Principal is Not Found");
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Find Interview Progress By Area {}", e.toString());
            return null;
        }
    }
}