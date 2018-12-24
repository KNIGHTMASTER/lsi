package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestRoleDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Role End Point", value = "Api Scaffolding Role", description = "Api Scaffolding for Entity Role")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.ROLE)
public interface IRoleEndPoint extends IScaffoldingEndPoint<RequestRoleDTO, ResponseRoleDTO> {

    @PostMapping("/findRolesByUserName")
    GenericListResponseDTO<ResponseRoleDTO> findRolesByUserName(@RequestBody RequestFindByNameDTO p_RequestFindByNameDTO) throws EndPointException;
}
