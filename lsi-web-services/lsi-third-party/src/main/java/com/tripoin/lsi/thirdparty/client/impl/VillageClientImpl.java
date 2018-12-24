package com.tripoin.lsi.thirdparty.client.impl;

import com.tripoin.lsi.thirdparty.client.IVillageClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class VillageClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseVillageDTO> findVillageById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IVillageClient villageClient = FeignBuilderFactory.createClient(IVillageClient.class, aclApiBasePath);
        return villageClient.findVillageById(p_Bearer, p_RequestFindByIdDTO);
    }
}
