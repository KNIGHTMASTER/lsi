package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestAgentAssignmentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestFindUserAgentByIdDTO;
import com.tripoin.lsi.core.data.dto.request.RequestFindUserAgentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInsertAssignUserAgentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailUserAgentById;
import com.tripoin.lsi.core.data.dto.response.ResponseUserAgentDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "User Agent End Point", value = "Api User Agent", description = "Api User Agent")
@RequestMapping("/secured/userAgent")
public interface IUserAgentEndPoint {

    @ApiOperation(value = "Find User Agents")
    @PostMapping("/findUserAgents")
    Page<ResponseUserAgentDTO> findUserAgents(HttpServletRequest httpServletRequest, @RequestBody RequestFindUserAgentDTO p_RequestFindByIdDTO) throws EndPointException;

    @ApiOperation(value = "Find User Agents")
    @PostMapping("/findUserAgentById")
    ResponseDetailUserAgentById findUserAgentById(HttpServletRequest httpServletRequest, @RequestBody RequestFindUserAgentByIdDTO p_RequestFindUserAgentByIdDTO) throws EndPointException;

    @ApiOperation(value = "Insert User Agents")
    @PostMapping("/insertUserAgent")
    ResponseData insertUserAgent(HttpServletRequest httpServletRequest, @RequestBody RequestInsertAssignUserAgentDTO p_RequestInsertAssignUserAgentDTO) throws EndPointException;

    @ApiOperation(value = "Assign User Agent")
    @PostMapping("/assignUserAgent")
    ResponseData assignUserAgent(HttpServletRequest httpServletRequest, @RequestBody RequestAgentAssignmentDTO p_RequestAgentAssignmentDTO) throws EndPointException;
}
