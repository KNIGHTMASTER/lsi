package com.wissensalt.acl.endpoint.core;

import com.wissensalt.acl.ACLConstant;
import com.wissensalt.acl.dto.request.RequestAgentLimitDTO;
import com.wissensalt.acl.dto.request.RequestAgentPaginationDTO;
import com.wissensalt.acl.dto.request.RequestUpdatePhoneNumberPasswordDTO;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestUserDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.*;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.base.ResponseNameDTO;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.security.ResponseUserAgentDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "User End Point", value = "Api Scaffolding User", description = "Api Scaffolding for Entity User")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.USER)
public interface IUserEndPoint extends IScaffoldingEndPoint<RequestUserDTO, ResponseUserDTO> {

    @PostMapping("/getPhoneNumberEmailByUserId")
    ResponsePhoneNumberEmail getPhoneNumberByUserId(@RequestBody RequestFindByIdDTO requestFindByIdDTO) throws EndPointException;

    @PostMapping("/getPhoneNumberEmailByName")
    ResponsePhoneNumberEmail getPhoneNumberByUserName(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException;

    @PostMapping("/findVolunteerByCodeContainingAndNameContaining")
    List<ResponseUserDTO> findVolunteerByCodeContainingAndNameContaining(@RequestBody RequestFindByCodeNameDTO p_RequestFindByCodeNameDTO) throws EndPointException;

    @PostMapping("/findVolunteerByCodeContaining")
    List<ResponseUserDTO> findVolunteerByCodeContaining(@RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException;

    @PostMapping("/findVolunteerByNameContaining")
    List<ResponseUserDTO> findVolunteerByNameContaining(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException;

    @GetMapping("/findAllVolunteer")
    List<ResponseUserDTO> findAllVolunteer() throws EndPointException;

    @PostMapping("/insertUserAgent")
    ResponseUserDTO insertUserAgent(@RequestBody RequestInsertUserAgentDTO p_RequestInsertUserAgentDTO) throws EndPointException;

    @ApiOperation(value = "Find LOV User Agent Like Limit")
    @PostMapping(ACLConstant.Path.FIND_LOV_AGENT_LIMIT)
    GenericListResponseDTO<ResponseUserDTO> findLovAgentLimit(HttpServletRequest httpServletRequest, @RequestBody RequestAgentLimitDTO p_RequestAgentLimitDTO) throws EndPointException;

    @ApiOperation(value = "Find Pagination Agent By Name UserName")
    @PostMapping(ACLConstant.Path.FIND_PAGINATION_AGENT_BY_NAME_USERNAME)
    Page<ResponseUserAgentDTO> findPaginationAgentByCodeAndNameLike(HttpServletRequest httpServletRequest, @RequestBody RequestAgentPaginationDTO p_RequestAgentPaginationDTO) throws EndPointException;

    @ApiOperation(value = "Find Agent By Name UserName")
    @PostMapping(ACLConstant.Path.FIND_AGENT_BY_USERNAME)
    ResponseUserDTO findAgentByUserName(HttpServletRequest httpServletRequest, @RequestBody RequestFindByCodeDTO p_RequestFindByCodeDTO) throws EndPointException;

    @ApiOperation(value = "Update Phone Number")
    @PostMapping(ACLConstant.Path.UPDATE_AGENT_ATTRIBUTE)
    ResponseData updateAgentAttribute(HttpServletRequest httpServletRequest, @RequestBody RequestUpdatePhoneNumberPasswordDTO p_RequestUpdatePhoneNumberPasswordDTO) throws EndPointException;

    @GetMapping("/getPrincipal")
    @ResponseBody
    ResponseNameDTO getPrincipal(Principal p_Principal);
}
