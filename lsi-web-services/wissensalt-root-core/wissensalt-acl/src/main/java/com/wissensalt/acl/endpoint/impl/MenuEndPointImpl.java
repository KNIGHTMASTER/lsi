package com.wissensalt.acl.endpoint.impl;

import com.wissensalt.acl.dto.request.RequestAssignMenuToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuAndAssignToRoleDTO;
import com.wissensalt.acl.dto.request.RequestCreateMenuDTO;
import com.wissensalt.acl.dto.request.RequestMergeMenuToRole;
import com.wissensalt.acl.dto.response.MenuByRoleResponseDTO;
import com.wissensalt.acl.dto.response.MenuResponseDTO;
import com.wissensalt.acl.endpoint.IMenuEndPoint;
import com.wissensalt.acl.service.IMenuService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created on 8/8/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class MenuEndPointImpl implements IMenuEndPoint{

    @Autowired
    private IMenuService menuService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuEndPointImpl.class);

    @RequestLogger(name = "acl|create-menu")
    @Override
    public ResponseData createMenu(HttpServletRequest httpServletRequest, @RequestBody List<RequestCreateMenuDTO> p_RequestCreateMenuDTOs) throws EndPointException {
        ResponseData responseData;
        if (p_RequestCreateMenuDTOs.size() > 0) {
            try {
                responseData = menuService.createMenu(p_RequestCreateMenuDTOs);
            } catch (ServiceException e) {
                LOGGER.error("Error create menu {} ", e.toString());
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "acl-create-menu-assign-to-role")
    @Override
    public ResponseData createMenuAndAssignToRole(HttpServletRequest httpServletRequest, @RequestBody List<RequestCreateMenuAndAssignToRoleDTO> p_RequestCreateMenuAndAssignToRoleDTOs) throws EndPointException {
        ResponseData responseData;
        if (p_RequestCreateMenuAndAssignToRoleDTOs.size() > 0) {
            try {
                responseData = menuService.createMenuAndAssignToRole(p_RequestCreateMenuAndAssignToRoleDTOs);
            } catch (ServiceException e) {
                LOGGER.error("Error create menu {} ", e.toString());
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "acl|assign-menu-to-role")
    @Override
    public ResponseData assignMenuToRole(HttpServletRequest httpServletRequest, @RequestBody List<RequestAssignMenuToRoleDTO> p_RequestAssignMenuToRoleDTOList) throws EndPointException {
        ResponseData responseData;
        if (p_RequestAssignMenuToRoleDTOList.size() > 0) {
            try {
                responseData = menuService.assignMenuToRole(p_RequestAssignMenuToRoleDTOList);
            } catch (ServiceException e) {
                LOGGER.error("Error create menu {} ", e.toString());
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "acl|generate-menu")
    @Override
    public MenuResponseDTO generateMenu(HttpServletRequest httpServletRequest, Principal p_Principal) throws EndPointException {
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));

        if (p_Principal.getName() != null) {
            LOGGER.info("Principal {} Found", p_Principal.getName());
            try {
                menuResponseDTO = menuService.generateMenu(p_Principal.getName());
            } catch (ServiceException e) {
                LOGGER.error("Error Generating Menu for {} - {}", p_Principal.getName(), e.toString());
            }
        }else {
            LOGGER.warn("Principal Not Found");
            menuResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }
        return menuResponseDTO;
    }

    @RequestLogger(name = "acl|find-menu-by-role")
    @Override
    public MenuByRoleResponseDTO findMenuByRole(HttpServletRequest httpServletRequest, RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException {
        MenuByRoleResponseDTO menuResponseDTO = new MenuByRoleResponseDTO();
        menuResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
        try {
            menuResponseDTO = menuService.findMenuByRole(p_RequestFindByIdDTO.getId());
        } catch (ServiceException e) {
            menuResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
            LOGGER.error("Error Find Menu By Role {}", e.toString());
        }
        return menuResponseDTO;
    }

    @RequestLogger(name = "acl|merge-menu-to-role")
    @Override
    public ResponseData mergeMenuToRole(HttpServletRequest httpServletRequest, @RequestBody RequestMergeMenuToRole p_RequestMergeMenuToRole) throws EndPointException {
        ResponseData responseData;
        try {
            responseData = menuService.mergeMenuToRole(p_RequestMergeMenuToRole);
        } catch (ServiceException e) {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            LOGGER.error("Error Merge Menu To Role {}", e.toString());
        }
        return responseData;
    }
}
