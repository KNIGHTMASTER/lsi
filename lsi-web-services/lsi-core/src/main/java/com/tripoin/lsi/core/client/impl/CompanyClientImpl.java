package com.tripoin.lsi.core.client.impl;

import com.tripoin.lsi.core.client.ICompanyClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseCompanyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 9/18/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class CompanyClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseCompanyDTO> findCompanyById(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        ICompanyClient companyClient = FeignBuilderFactory.createClient(ICompanyClient.class, aclApiBasePath);
        return companyClient.findCompanyById(p_Bearer, p_RequestFindByIdDTO);
    }
}
