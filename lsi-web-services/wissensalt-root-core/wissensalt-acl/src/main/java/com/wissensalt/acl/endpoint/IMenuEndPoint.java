package com.wissensalt.acl.endpoint;

import com.wissensalt.acl.ACLConstant;
import com.wissensalt.acl.dto.request.RequestAssignMenuToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuAndAssignToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuDTO;
import com.wissensalt.acl.dto.request.RequestMergeMenuToRole;
import com.wissensalt.acl.dto.response.MenuByRoleResponseDTO;
import com.wissensalt.acl.dto.response.MenuResponseDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 7/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Menu and Authorization", value = "API Menu and Authorization", description = "Rest Web Service for Handling Authorization")
@RequestMapping(ACLConstant.Path.MENU)
public interface IMenuEndPoint {

    @ApiOperation(value = "create menu")
    @PostMapping(ACLConstant.Path.CREATE_MENU)
    ResponseData createMenu(HttpServletRequest httpServletRequest, @RequestBody List<RequestCreateMenuDTO> p_RequestCreateMenuDTOs) throws EndPointException;

    @ApiOperation(value = "create menu and assign to role")
    @PostMapping(ACLConstant.Path.CREATE_MENU_ASSIGN_TO_ROLE)
    ResponseData createMenuAndAssignToRole(HttpServletRequest httpServletRequest, @RequestBody List<RequestCreateMenuAndAssignToRoleDTO> p_RequestCreateMenuAndAssignToRoleDTOs) throws EndPointException;

    @ApiOperation(value = "assign menus to role")
    @PostMapping(ACLConstant.Path.ASSIGN_MENU_TO_ROLE)
    ResponseData assignMenuToRole(HttpServletRequest httpServletRequest, @RequestBody List<RequestAssignMenuToRoleDTO> p_RequestAssignMenuToRoleDTOList) throws EndPointException;

    @ApiOperation(value = "merge menus to role")
    @PostMapping(ACLConstant.Path.MERGE_MENU_TO_ROLE)
    ResponseData mergeMenuToRole(HttpServletRequest httpServletRequest, @RequestBody RequestMergeMenuToRole p_RequestMergeMenuToRole) throws EndPointException;

    @ApiOperation(value = "generate menu")
    @PostMapping(ACLConstant.Path.GENERATE_MENU)
    MenuResponseDTO generateMenu(HttpServletRequest httpServletRequest, Principal principal) throws EndPointException;

    @ApiOperation(value = "Find Menu By Role")
    @PostMapping(ACLConstant.Path.FIND_MENU_BY_ROLE)
    MenuByRoleResponseDTO findMenuByRole(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException;
}
