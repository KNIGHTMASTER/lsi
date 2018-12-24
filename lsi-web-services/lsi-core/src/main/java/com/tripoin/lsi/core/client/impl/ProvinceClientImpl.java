package com.tripoin.lsi.core.client.impl;

import com.tripoin.lsi.core.client.IProvinceClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ProvinceClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseProvinceDTO> findProvinceById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IProvinceClient provinceClient = FeignBuilderFactory.createClient(IProvinceClient.class, aclApiBasePath);
        return provinceClient.findProvinceById(p_Bearer, p_RequestFindByIdDTO);
    }
}
