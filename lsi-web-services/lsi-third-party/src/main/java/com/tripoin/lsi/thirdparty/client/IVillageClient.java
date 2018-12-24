package com.tripoin.lsi.thirdparty.client;

import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_ACCEPT_JSON;
import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_CONTENT_TYPE;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IVillageClient {

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/village/findById")
    GenericSingleDATAResponseDTO<ResponseVillageDTO> findVillageById(@Param("auth") String p_BearerToken, RequestFindByIdDTO p_RequestFindByIdDTO);
}
