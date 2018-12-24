package com.tripoin.lsi.core.client.impl;

import com.tripoin.lsi.core.client.IDistrictClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseDistrictDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class DistrictClientImpl {
    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseDistrictDTO> findDistrictById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IDistrictClient districtClient = FeignBuilderFactory.createClient(IDistrictClient.class, aclApiBasePath);
        return districtClient.findDistrictById(p_Bearer, p_RequestFindByIdDTO);
    }
}
