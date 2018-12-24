package com.tripoin.lsi.thirdparty.client.impl;

import com.tripoin.lsi.thirdparty.client.IBranchClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseBranchDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class BranchClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseBranchDTO> findBranchById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IBranchClient branchClient = FeignBuilderFactory.createClient(IBranchClient.class, aclApiBasePath);
        return branchClient.findBranchById(p_Bearer, p_RequestFindByIdDTO);
    }
}
