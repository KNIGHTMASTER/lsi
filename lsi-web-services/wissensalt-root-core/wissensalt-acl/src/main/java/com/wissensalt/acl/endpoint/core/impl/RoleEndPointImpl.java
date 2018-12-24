package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.acl.endpoint.core.IRoleEndPoint;
import com.wissensalt.scaffolding.dto.request.RequestRoleDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.IRoleService;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.entity.mapper.security.RoleMapper;
import com.wissensalt.shared.entity.security.SecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class RoleEndPointImpl extends AScaffoldingEndPoint<SecurityRole, RequestRoleDTO, ResponseRoleDTO> implements IRoleEndPoint {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = roleService;
        dataMapperIntegration = roleMapper;
    }

    @Override
    public GenericListResponseDTO<ResponseRoleDTO> findRolesByUserName(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException {
        try {
            return scaffoldingResponseBuilder.buildListDTOResponse(roleService.findRolesByUserName(p_RequestFindByNameDTO.getName()));
        } catch (ServiceException e) {
            LOGGER.error("Error Find Roles By User Name {}", e.toString());
            return null;
        }
    }
}
