package com.tripoin.lsi.core.client;

import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
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
public interface IRoleClient {

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/role/findRolesByUserName")
    GenericListResponseDTO<ResponseRoleDTO> findRolesByUserName(@Param("auth") String p_BearerToken, RequestFindByNameDTO p_RequestFindByNameDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/role/findByCode")
    GenericSingleDATAResponseDTO<ResponseRoleDTO> findRoleByCode(@Param("auth") String p_BearerToken, RequestFindByCodeDTO p_RequestFindByCodeDTO);
}
