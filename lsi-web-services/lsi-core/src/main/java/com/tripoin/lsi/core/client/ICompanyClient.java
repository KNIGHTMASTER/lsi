package com.tripoin.lsi.core.client;

import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseCompanyDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_ACCEPT_JSON;
import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_CONTENT_TYPE;

/**
 * Created on 9/18/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface ICompanyClient {

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/company/findById")
    GenericSingleDATAResponseDTO<ResponseCompanyDTO> findCompanyById(@Param("auth") String p_BearerToken, RequestFindByIdDTO p_RequestFindByIdDTO);
}
