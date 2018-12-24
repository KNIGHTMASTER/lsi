package com.tripoin.lsi.core.client.impl;

import com.tripoin.lsi.core.client.IRoleClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class RoleClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericListResponseDTO<ResponseRoleDTO> findRolesByUserName(String p_Bearer, RequestFindByNameDTO p_RequestFindByNameDTO) {
        IRoleClient roleClient = FeignBuilderFactory.createClient(IRoleClient.class, aclApiBasePath);
        return roleClient.findRolesByUserName(p_Bearer, p_RequestFindByNameDTO);
    }

    public GenericSingleDATAResponseDTO<ResponseRoleDTO> findRoleByCode(String p_Bearer, RequestFindByCodeDTO p_RequestFindByCodeDTO) {
        IRoleClient roleClient = FeignBuilderFactory.createClient(IRoleClient.class, aclApiBasePath);
        return roleClient.findRoleByCode(p_Bearer, p_RequestFindByCodeDTO);
    }
}
