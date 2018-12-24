package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestAgentAssignmentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestFindUserAgentByIdDTO;
import com.tripoin.lsi.core.data.dto.request.RequestFindUserAgentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInsertAssignUserAgentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailUserAgentById;
import com.tripoin.lsi.core.data.dto.response.ResponseUserAgentDTO;
import com.tripoin.lsi.core.endpoint.IUserAgentEndPoint;
import com.tripoin.lsi.core.service.IUserAgentService;
import com.tripoin.lsi.core.service.impl.UserAgentServiceImpl;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class UserAgentEndPointImpl implements IUserAgentEndPoint {

    @Autowired
    private IUserAgentService userAgentService;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAgentServiceImpl.class);

    @RequestLogger(name = "User Agent | Find User Agents")
    @Override
    public Page<ResponseUserAgentDTO> findUserAgents(HttpServletRequest httpServletRequest, @RequestBody RequestFindUserAgentDTO p_RequestFindUserAgentDTO) throws EndPointException {
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestFindUserAgentDTO);
        try {
            return userAgentService.findUserAgents(bearer, p_RequestFindUserAgentDTO, pageRequest);
        } catch (ServiceException e) {
            LOGGER.error("Error Find User Agents {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "User Agent | Find User Agent By Id")
    @Override
    public ResponseDetailUserAgentById findUserAgentById(HttpServletRequest httpServletRequest, @RequestBody RequestFindUserAgentByIdDTO p_RequestFindUserAgentByIdDTO) throws EndPointException {
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            return userAgentService.findUserAgentById(bearer, p_RequestFindUserAgentByIdDTO.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error Find User Agents {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "User Agent | Insert User Agent")
    @Override
    public ResponseData insertUserAgent(HttpServletRequest httpServletRequest, @RequestBody RequestInsertAssignUserAgentDTO p_RequestInsertAssignUserAgentDTO) throws EndPointException {
        ResponseData responseData;
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            responseData = userAgentService.insertUserAgent(bearer, p_RequestInsertAssignUserAgentDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Insert User Agent {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "User Agent | Assign User Agent")
    @Override
    public ResponseData assignUserAgent(HttpServletRequest httpServletRequest, @RequestBody RequestAgentAssignmentDTO p_RequestAgentAssignmentDTO) throws EndPointException {
        ResponseData responseData;
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            responseData = userAgentService.assignUserAgent(bearer, p_RequestAgentAssignmentDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Assign User Agent {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }
}
