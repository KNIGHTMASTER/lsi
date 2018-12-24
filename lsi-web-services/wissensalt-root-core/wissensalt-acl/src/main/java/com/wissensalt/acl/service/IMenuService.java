package com.wissensalt.acl.service;

import com.wissensalt.acl.dto.request.RequestAssignMenuToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuAndAssignToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuDTO;
import com.wissensalt.acl.dto.request.RequestMergeMenuToRole;
import com.wissensalt.acl.dto.response.MenuByRoleResponseDTO;
import com.wissensalt.acl.dto.response.MenuResponseDTO;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IEntityGenerator;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.entity.security.SecurityFunction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 8/8/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IMenuService extends IEntityGenerator<SecurityFunction, RequestCreateMenuDTO> {

    @Transactional
    ResponseData createMenu(List<RequestCreateMenuDTO> p_RequestCreateMenuDTOs) throws ServiceException;

    @Transactional
    ResponseData createMenuAndAssignToRole(List<RequestCreateMenuAndAssignToRoleDTO> p_RequestCreateMenuAndAssignToRoleDTOs) throws ServiceException;

    @Transactional
    ResponseData assignMenuToRole(List<RequestAssignMenuToRoleDTO> p_RequestAssignMenuToRoleDTOList) throws ServiceException;

    @Transactional
    ResponseData mergeMenuToRole(RequestMergeMenuToRole p_RequestMergeMenuToRole) throws ServiceException;

    MenuResponseDTO generateMenu(String p_PrincipalName) throws ServiceException;

    MenuByRoleResponseDTO findMenuByRole(Long p_RoleId) throws ServiceException;
}
