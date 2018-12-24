package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInsertInterviewDetailResultDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringIncludeAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileFindDetailAssignmentByAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileInterviewComponent;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobilePointFilteringDTO;
import com.tripoin.lsi.core.endpoint.IInterviewHeaderMobileEndPoint;
import com.tripoin.lsi.core.service.IInterviewDetailService;
import com.tripoin.lsi.core.service.IInterviewHeaderMobileService;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewHeaderMobileEndPointImpl implements IInterviewHeaderMobileEndPoint {

    @Autowired
    private IInterviewHeaderMobileService interviewHeaderMobileService;

    @Autowired
    private IInterviewDetailService interviewDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewHeaderMobileEndPointImpl.class);

    @RequestLogger(name = "Interview Header Mobile | Find Detail Assignment By Agent")
    @Override
    public ResponseMobileFindDetailAssignmentByAgentDTO findDetailAssignmentAgent(HttpServletRequest p_HttpServletRequest, Principal p_Principal) throws EndPointException {
        ResponseMobileFindDetailAssignmentByAgentDTO response = new ResponseMobileFindDetailAssignmentByAgentDTO();
        String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
        try {
            response = interviewHeaderMobileService.findDetailAssignment(bearer, p_Principal);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Detail Assignment Agent {}", e.toString());
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

    @RequestLogger(name = "Interview Header Mobile | Find Interview Component By Principal")
    @Override
    public ResponseMobileInterviewComponent findInterviewComponentByPrincipal(HttpServletRequest p_HttpServletRequest, Principal p_Principal) throws EndPointException {
        ResponseMobileInterviewComponent response = new ResponseMobileInterviewComponent();
        String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
        try {
            response = interviewHeaderMobileService.findInterviewComponentByPrincipal(bearer, p_Principal);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Interview Component By Principal {}", e.toString());
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

    @RequestLogger(name = "Interview Header Mobile | Find Point Filtering")
    @Override
    public ResponseMobilePointFilteringDTO findPointFiltering(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestMobilePointFilteringDTO p_RequestMobilePointFilteringDTO) throws EndPointException {
        ResponseMobilePointFilteringDTO response = new ResponseMobilePointFilteringDTO();
        String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
        try {
            response = interviewHeaderMobileService.findPointFiltering(bearer, p_Principal, p_RequestMobilePointFilteringDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Point Filtering {}", e.toString());
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

    @RequestLogger(name = "Interview Header Mobile | Find Point Filtering Include Agent")
    @Override
    public ResponseMobilePointFilteringDTO findPointFilteringIncludeAgent(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestMobilePointFilteringIncludeAgentDTO p_RequestMobilePointFilteringIncludeAgentDTO) throws EndPointException {
        ResponseMobilePointFilteringDTO response = new ResponseMobilePointFilteringDTO();
        String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
        try {
            return interviewHeaderMobileService.findPointFilteringIncludeAgent(bearer, p_Principal, p_RequestMobilePointFilteringIncludeAgentDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Point Filtering Include Agent {}", e.toString());
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return response;
    }

    @Override
    public ResponseData insertInterviewDetailResult(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestInsertInterviewDetailResultDTO p_RequestInsertInterviewDetailResultDTO) throws EndPointException {
        ResponseData responseData;
        try {
            responseData = interviewDetailService.insertInterviewDetailResult(p_RequestInsertInterviewDetailResultDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Insert Interview Detail Result {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseCode());
        }
        return responseData;
    }
}
