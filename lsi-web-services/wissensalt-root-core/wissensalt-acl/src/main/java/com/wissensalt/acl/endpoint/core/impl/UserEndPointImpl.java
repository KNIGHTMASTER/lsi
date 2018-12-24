package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.acl.dto.request.RequestAgentLimitDTO;
import com.wissensalt.acl.dto.request.RequestAgentPaginationDTO;
import com.wissensalt.acl.dto.request.RequestUpdatePhoneNumberPasswordDTO;
import com.wissensalt.acl.endpoint.core.IUserEndPoint;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestUserDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.IUserService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.*;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.base.ResponseNameDTO;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.security.ResponseUserAgentDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import com.wissensalt.shared.entity.mapper.security.UserMapper;
import com.wissensalt.shared.entity.security.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.USER)
public class UserEndPointImpl extends AScaffoldingEndPoint<SecurityUser, RequestUserDTO, ResponseUserDTO> implements IUserEndPoint {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = userService;
        dataMapperIntegration = userMapper;
    }

    @RequestLogger(name = "acl|get-phone-number-email-by-user-id")
    @Override
    public ResponsePhoneNumberEmail getPhoneNumberByUserId(@RequestBody RequestFindByIdDTO requestFindByIdDTO) throws EndPointException {
        if (requestFindByIdDTO.getId() != null) {
            try {
                return userService.getPhoneNumberEmailByUserId(requestFindByIdDTO.getId());
            } catch (ServiceException e) {
                LOGGER.error("Error Get Phone Number By User Id {}", e.toString());
                return null;
            }
        }else {
            return null;
        }
    }

    @RequestLogger(name = "acl|get-phone-number-email-by-user-name")
    @Override
    public ResponsePhoneNumberEmail getPhoneNumberByUserName(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException {
        if (p_RequestFindByNameDTO.getName() != null) {
            try {
                return userService.getPhoneNumberEmailByUserName(p_RequestFindByNameDTO.getName());
            } catch (ServiceException e) {
                LOGGER.error("Error Get Phone Number By User Id {}", e.toString());
                return null;
            }
        }else {
            return null;
        }
    }

    @RequestLogger(name = "acl|find-volunteer-by-code-containing-and-name-containing")
    @Override
    public List<ResponseUserDTO> findVolunteerByCodeContainingAndNameContaining(@RequestBody RequestFindByCodeNameDTO p_RequestFindByCodeNameDTO) throws EndPointException {
        try {
            return userService.findVolunteerByCodeContainingAndNameContaining(p_RequestFindByCodeNameDTO.getCode(), p_RequestFindByCodeNameDTO.getName());
        } catch (ServiceException e) {
            LOGGER.error("Error Find By Code and Name Containing {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-volunteer-by-code-containing")
    @Override
    public List<ResponseUserDTO> findVolunteerByCodeContaining(@RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException {
        try {
            return userService.findVolunteerByCodeContaining(p_RequestFindByCodeDTO.getCode());
        } catch (ServiceException e) {
            LOGGER.error("Error Find By Code Containing {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-volunteer-by-name-containing")
    @Override
    public List<ResponseUserDTO> findVolunteerByNameContaining(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException {
        try {
            return userService.findVolunteerByNameContaining(p_RequestFindByNameDTO.getName());
        } catch (ServiceException e) {
            LOGGER.error("Error Find By Name Containing {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-all-volunteer")
    @Override
    public List<ResponseUserDTO> findAllVolunteer() throws EndPointException {
        try {
            return userService.findAllVolunteer();
        } catch (ServiceException e) {
            LOGGER.error("Error Find All Volunteer {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|insert-user-agent")
    @Override
    public ResponseUserDTO insertUserAgent(@RequestBody RequestInsertUserAgentDTO p_RequestInsertUserAgentDTO) throws EndPointException {
        try {
            return userService.insertUserAgent(p_RequestInsertUserAgentDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Insert User Agent {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-lov-agent-limit")
    @Override
    public GenericListResponseDTO<ResponseUserDTO> findLovAgentLimit(HttpServletRequest httpServletRequest, @RequestBody RequestAgentLimitDTO p_RequestAgentLimitDTO) throws EndPointException {
        try {
            return scaffoldingResponseBuilder.buildListDTOResponse(
                    userService.findLovAgentLimit(
                            p_RequestAgentLimitDTO.getUserName(), p_RequestAgentLimitDTO.getName(), p_RequestAgentLimitDTO.getLimit()
                    )
            );
        } catch (ServiceException e) {
            LOGGER.error("Error Find LOV Agent Limit {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-pagination-by-code-and-name-like")
    @Override
    public Page<ResponseUserAgentDTO> findPaginationAgentByCodeAndNameLike(HttpServletRequest httpServletRequest, @RequestBody RequestAgentPaginationDTO p_RequestAgentPaginationDTO) throws EndPointException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestAgentPaginationDTO);
        try {
            return userService.findPaginationAgentByCodeAndNameLike(p_RequestAgentPaginationDTO.getUserName(), p_RequestAgentPaginationDTO.getName(), pageRequest);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Pagination Agent By Code And Name Like {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "acl|find-agent-by-username")
    @Override
    public ResponseUserDTO findAgentByUserName(HttpServletRequest httpServletRequest, @RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException {
        ResponseUserDTO result = new ResponseUserDTO();
        try {
            result =  userService.findVolunteerByUserName(p_RequestFindByCodeDTO.getCode());
        } catch (ServiceException e) {
            LOGGER.error("Error Find Agent By UserName {}", e.toString());
        }
        return result;
    }

    @RequestLogger(name = "acl|update-phone-number")
    @Override
    public ResponseData updateAgentAttribute(HttpServletRequest httpServletRequest, @RequestBody RequestUpdatePhoneNumberPasswordDTO p_RequestUpdatePhoneNumberPasswordDTO) throws EndPointException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        try {
            responseData = userService.updateAgentAttribute(
                    p_RequestUpdatePhoneNumberPasswordDTO.getUserName(),
                    p_RequestUpdatePhoneNumberPasswordDTO.getName(),
                    p_RequestUpdatePhoneNumberPasswordDTO.getPhoneNumber(),
                    p_RequestUpdatePhoneNumberPasswordDTO.getOldPassword(),
                    p_RequestUpdatePhoneNumberPasswordDTO.getNewPassword()
            );
        } catch (ServiceException e) {
            LOGGER.error("Error Update Phone Number And Password {}", e.toString());
        }
        return responseData;
    }

    @RequestLogger(name = "user|find-all-pagination-except-volunteer")
    @Override
    public Page<ResponseUserDTO> findPaginationAll(@RequestBody RequestPaginationAllDTO p_RequestPaginationAll, HttpServletRequest p_HttpServletRequest) throws EndPointException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestPaginationAll);
        return scaffoldingResponseBuilder.buildPageDTOResponse(pageRequest, userService.findAll(pageRequest), userMapper);
    }

    @RequestLogger(name = "user|get-principal")
    @Override
    public ResponseNameDTO getPrincipal(Principal p_Principal) {
        ResponseNameDTO responseNameDTO = new ResponseNameDTO();
        responseNameDTO.setName(p_Principal.getName());
        return responseNameDTO;
    }
}
