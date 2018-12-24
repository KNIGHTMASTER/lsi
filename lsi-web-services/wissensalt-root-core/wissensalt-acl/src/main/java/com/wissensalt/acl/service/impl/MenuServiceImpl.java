package com.wissensalt.acl.service.impl;

import com.wissensalt.acl.dto.request.*;
import com.wissensalt.acl.dto.response.ContentMenuResponseByRoleDTO;
import com.wissensalt.acl.dto.response.ContentMenuResponseDTO;
import com.wissensalt.acl.dto.response.MenuByRoleResponseDTO;
import com.wissensalt.acl.dto.response.MenuResponseDTO;
import com.wissensalt.acl.service.IMenuService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.core.IFunctionAssignmentService;
import com.wissensalt.scaffolding.service.core.IFunctionService;
import com.wissensalt.scaffolding.service.core.IRoleService;
import com.wissensalt.shared.constant.CommonConstant;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.entity.security.SecurityFunction;
import com.wissensalt.shared.entity.security.SecurityFunctionAssignment;
import com.wissensalt.shared.entity.security.SecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/8/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFunctionAssignmentService functionAssignmentService;

    @Autowired
    private IFunctionService functionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    public ResponseData createMenu(List<RequestCreateMenuDTO> p_RequestCreateMenuDTOs) throws ServiceException {
        ResponseData responseData;
        List<SecurityFunction> securityFunctions = new ArrayList<>();
        for (RequestCreateMenuDTO contentMenu : p_RequestCreateMenuDTOs) {
            SecurityFunction securityFunction = generateEntityForCommonTrx(contentMenu);
            securityFunctions.add(securityFunction);
        }
        if (securityFunctions.size() > 0) {
            functionService.insertCollections(securityFunctions);
            responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            LOGGER.error("Security Functions is Empty");
        }

        return responseData;
    }

    @Override
    public ResponseData createMenuAndAssignToRole(List<RequestCreateMenuAndAssignToRoleDTO> p_RequestCreateMenuAndAssignToRoleDTOs) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        for (RequestCreateMenuAndAssignToRoleDTO contentAssign : p_RequestCreateMenuAndAssignToRoleDTOs) {
            SecurityRole securityRole = roleService.findById(contentAssign.getRoleId());
            if (securityRole != null) {
                List<SecurityFunction> securityFunctions = new ArrayList<>();
                List<SecurityFunctionAssignment> functionAssignments = new ArrayList<>();
                for (RequestCreateMenuDTO itemMenu : contentAssign.getMenuList()) {
                    SecurityFunction securityFunction = generateEntityForCommonTrx(itemMenu);
                    securityFunctions.add(securityFunction);

                    SecurityFunctionAssignment functionAssignment = new SecurityFunctionAssignment();
                    functionAssignment.setName("Assignment ".concat(securityRole.getName()).concat(CommonConstant.Punctuation.HYPHEN).concat(securityFunction.getName()));
                    functionAssignment.setSecurityFunction(securityFunction);
                    functionAssignment.setRole(securityRole);
                    functionAssignments.add(functionAssignment);
                }
                if (securityFunctions.size() > 0 && functionAssignments.size() > 0) {
                    functionService.insertCollections(securityFunctions);
                    functionAssignmentService.insertCollections(functionAssignments);
                }else {
                    responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                    LOGGER.error("Security Functions is Empty || Function Assignment is Empty");
                }
            }else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                LOGGER.warn("Role Not Found");
            }
        }
        return responseData;
    }

    @Override
    public ResponseData assignMenuToRole(List<RequestAssignMenuToRoleDTO> p_RequestAssignMenuToRoleDTOList) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        for (RequestAssignMenuToRoleDTO menuToRoleDTO : p_RequestAssignMenuToRoleDTOList) {
            SecurityRole securityRole = roleService.findById(menuToRoleDTO.getRoleId());
            if (securityRole != null) {
                for (Long functionId : menuToRoleDTO.getMenuIds()) {
                    SecurityFunction securityFunction = functionService.findById(functionId);
                    if (securityFunction != null) {
                        SecurityFunctionAssignment functionAssignment = new SecurityFunctionAssignment();
                        functionAssignment.setRole(securityRole);
                        functionAssignment.setSecurityFunction(securityFunction);
                        functionAssignmentService.insert(functionAssignment);
                    }else {
                        responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                        LOGGER.error("Function is Not Found");
                        break;
                    }
                }
            }else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                LOGGER.error("Role is not Found");
                break;
            }
        }
        return responseData;
    }

    @Override
    public ResponseData mergeMenuToRole(RequestMergeMenuToRole p_RequestMergeMenuToRole) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        SecurityRole securityRole = roleService.findById(p_RequestMergeMenuToRole.getRoleId());
        if (securityRole != null) {
            for (Map.Entry<Long, ContentMergeMenuToRole> mapMenuEnable : p_RequestMergeMenuToRole.getMapMenuEnable().entrySet()) {
                if (mapMenuEnable.getValue().getEnabled() == Boolean.TRUE) {
                    SecurityFunction securityFunction = functionService.findById(mapMenuEnable.getKey());
                    if (securityFunction != null) {
                        SecurityFunctionAssignment securityFunctionAssignment = functionAssignmentService.findByRoleIdAndSecurityFunction_id(securityRole.getId(), mapMenuEnable.getKey());
                        if (securityFunctionAssignment == null) {
                            securityFunctionAssignment = new SecurityFunctionAssignment();
                            securityFunctionAssignment.setRole(securityRole);
                            securityFunctionAssignment.setSecurityFunction(securityFunction);
                            securityFunctionAssignment.setAction(mapMenuEnable.getValue().getAction());
                            functionAssignmentService.insert(securityFunctionAssignment);
                        }else {
                            securityFunctionAssignment.setRole(securityRole);
                            securityFunctionAssignment.setSecurityFunction(securityFunction);
                            securityFunctionAssignment.setAction(mapMenuEnable.getValue().getAction());
                            functionAssignmentService.update(securityFunctionAssignment);
                        }
                    }else {
                        LOGGER.error("Function With ID {} is Not Found ", mapMenuEnable.getKey());
                    }
                }else {
                    SecurityFunctionAssignment securityFunctionAssignment = functionAssignmentService.findByRoleIdAndSecurityFunction_id(securityRole.getId(), mapMenuEnable.getKey());
                    if (securityFunctionAssignment != null) {
                        functionAssignmentService.delete(securityFunctionAssignment.getId());
                    } else {
                        LOGGER.error("Function Assignment is Not Found");
                    }
                }

            }
        }else {
            LOGGER.error("Role is Not Found");
        }
        return responseData;
    }

    @Override
    public MenuResponseDTO generateMenu(String p_PrincipalName) throws ServiceException {
        List<SecurityRole> securityRoles = roleService.findRolesByUserName(p_PrincipalName);
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
        if (securityRoles.size() > 0) {
            List<ContentMenuResponseDTO> contentMenu = new ArrayList<>();
            for (SecurityRole securityRole : securityRoles) {
                List<SecurityFunction> securityFunctions = new ArrayList<>();
                List<SecurityFunctionAssignment> securityFunctionAssignments = functionAssignmentService.findByRoleId(securityRole.getId());
                if (securityFunctionAssignments.size() > 0) {
                    for (SecurityFunctionAssignment securityFunctionAssignment : securityFunctionAssignments) {
                        securityFunctions.add(functionService.findById(securityFunctionAssignment.getSecurityFunction().getId()));
                    }
                }
                for (SecurityFunction securityFunction : securityFunctions) {
                    List<ContentMenuResponseDTO> firstItemMenu = new ArrayList<>();
                    if (securityFunction.getParentFunction() == null) {
                        ContentMenuResponseDTO itemMenu = getContentMenu(securityFunction, securityRole.getId());
                        itemMenu.setParent(null);

                        List<SecurityFunction> subFunctions = functionService.findByParentFunction(securityFunction);
                        if (subFunctions.size() > 0) {
                            for (SecurityFunction subFunction : subFunctions) {
                                ContentMenuResponseDTO itemSubMenu = getContentMenu(subFunction, securityRole.getId());
                                itemSubMenu.setParent(securityFunction.getId());
                                itemSubMenu.setSubMenu(null);
                                if (itemSubMenu.isAssigned()) {
                                    firstItemMenu.add(itemSubMenu);
                                }
                            }
                        }
                        itemMenu.setSubMenu(firstItemMenu);
                        contentMenu.add(itemMenu);
                    }
                }
                menuResponseDTO.setMenu(contentMenu);
            }
        }else {
            LOGGER.info("Role is not found, can not generate Menu");
        }
        return menuResponseDTO;
    }

    private ContentMenuResponseDTO getContentMenu(SecurityFunction p_SecurityFunction, Long p_RoleId) {
        ContentMenuResponseDTO itemMenu = new ContentMenuResponseDTO();
        itemMenu.setCode(p_SecurityFunction.getCode());
        itemMenu.setName(p_SecurityFunction.getName());
        if (p_SecurityFunction.getUrl() != null) {
            itemMenu.setLink(p_SecurityFunction.getUrl());
        }
        if (p_SecurityFunction.getStyle() != null) {
            itemMenu.setStyle(p_SecurityFunction.getStyle());
        }
        if (p_SecurityFunction.getOrder() != null) {
            itemMenu.setOrder(p_SecurityFunction.getOrder());
        }
        if (p_SecurityFunction.getStatus() != null) {
            itemMenu.setStatus(p_SecurityFunction.getStatus());
        }
        try {
            SecurityFunctionAssignment securityFunctionAssignment = functionAssignmentService.findByRoleIdAndSecurityFunction_id(p_RoleId, p_SecurityFunction.getId());
            if (securityFunctionAssignment != null) {
                itemMenu.setAssigned(true);
                itemMenu.setAction(securityFunctionAssignment.getAction());
            }else {
                itemMenu.setAssigned(false);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            itemMenu.setAssigned(false);
        }
        return itemMenu;
    }

    private ContentMenuResponseByRoleDTO getContentMenuByRole(SecurityFunction p_SecurityFunction, Long p_RoleId) {
        ContentMenuResponseByRoleDTO itemMenu = new ContentMenuResponseByRoleDTO();
        itemMenu.setId(p_SecurityFunction.getId());
        itemMenu.setCode(p_SecurityFunction.getCode());
        itemMenu.setName(p_SecurityFunction.getName());
        if (p_SecurityFunction.getUrl() != null) {
            itemMenu.setLink(p_SecurityFunction.getUrl());
        }
        if (p_SecurityFunction.getStyle() != null) {
            itemMenu.setStyle(p_SecurityFunction.getStyle());
        }
        if (p_SecurityFunction.getOrder() != null) {
            itemMenu.setOrder(p_SecurityFunction.getOrder());
        }
        if (p_SecurityFunction.getStatus() != null) {
            itemMenu.setStatus(p_SecurityFunction.getStatus());
        }
        try {
            SecurityFunctionAssignment securityFunctionAssignment = functionAssignmentService.findByRoleIdAndSecurityFunction_id(p_RoleId, p_SecurityFunction.getId());
            if (securityFunctionAssignment != null) {
                itemMenu.setAssigned(true);
                itemMenu.setAction(securityFunctionAssignment.getAction());
            }else {
                itemMenu.setAssigned(false);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            itemMenu.setAssigned(false);
        }
        return itemMenu;
    }

    @Override
    public SecurityFunction generateEntityForCommonTrx(RequestCreateMenuDTO contentMenu) throws ServiceException {
        SecurityFunction securityFunction = new SecurityFunction();
        if (contentMenu.getCode() != null) {
            securityFunction.setCode(contentMenu.getCode());
        }
        securityFunction.setName(contentMenu.getName());
        securityFunction.setRemarks(contentMenu.getRemarks());
        securityFunction.setStatus(CommonConstant.GeneralValue.ONE);
        securityFunction.setUrl(contentMenu.getUrl());
        securityFunction.setStyle(contentMenu.getStyle());
        securityFunction.setOrder(contentMenu.getOrder());
        SecurityFunction parentFunction = null;
        if (contentMenu.getParentMenu() != null) {
            try {
                parentFunction = functionService.findById(contentMenu.getParentMenu());
            } catch (ServiceException e) {
                LOGGER.error("Parent Function Not Found {} ", e.toString());
            }
            if (parentFunction != null) {
                securityFunction.setParentFunction(parentFunction);
            }
        }
        return securityFunction;
    }

    @Override
    public MenuByRoleResponseDTO findMenuByRole(Long p_RoleId) throws ServiceException {
        SecurityRole role = roleService.findById(p_RoleId);
        MenuByRoleResponseDTO menuByRoleResponseDTO = new MenuByRoleResponseDTO();
        menuByRoleResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
        if (role != null) {
            List<ContentMenuResponseByRoleDTO> contentActiveMenu = new ArrayList<>();
            List<SecurityFunction> securityFunctions = functionService.findAll();
            for (SecurityFunction securityFunction : securityFunctions) {
                List<ContentMenuResponseByRoleDTO> firstItemMenu = new ArrayList<>();
                if (securityFunction.getParentFunction() == null) {
                    ContentMenuResponseByRoleDTO itemMenu = getContentMenuByRole(securityFunction, role.getId());
                    itemMenu.setParent(null);

                    List<SecurityFunction> subFunctions = functionService.findByParentFunction(securityFunction);
                    if (subFunctions.size() > 0) {
                        for (SecurityFunction subFunction : subFunctions) {
                            ContentMenuResponseByRoleDTO itemSubMenu = getContentMenuByRole(subFunction, role.getId());
                            itemMenu.setStatus(subFunction.getStatus());
                            itemSubMenu.setParent(securityFunction.getId());
                            itemSubMenu.setSubMenu(null);
                            firstItemMenu.add(itemSubMenu);
                        }
                    }
                    itemMenu.setSubMenu(firstItemMenu);
                    contentActiveMenu.add(itemMenu);
                }
            }
            menuByRoleResponseDTO.setMenu(contentActiveMenu);
        }else {
            LOGGER.info("Role is not found, can not generate Menu");
        }
        return menuByRoleResponseDTO;
    }
}
