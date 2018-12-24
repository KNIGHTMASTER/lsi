package com.tripoin.lsi.core.client.impl;

import com.tripoin.lsi.core.client.ICityClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseCityDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class CityClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseCityDTO> findCityById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        ICityClient cityClient = FeignBuilderFactory.createClient(ICityClient.class, aclApiBasePath);
        return cityClient.findCityById(p_Bearer, p_RequestFindByIdDTO);
    }
}
