package com.tripoin.lsi.thirdparty.client;

import com.wissensalt.shared.dto.request.*;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_ACCEPT_JSON;
import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_CONTENT_TYPE;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IUserClient {

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findById")
    GenericSingleDATAResponseDTO<ResponseUserDTO> findUserById(@Param("auth") String p_BearerToken, RequestFindByIdDTO p_RequestFindByIdDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findByCode")
    GenericSingleDATAResponseDTO<ResponseUserDTO> findUserByUserName(@Param("auth") String p_BearerToken, RequestFindByCodeDTO p_RequestFindByCodeDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findByCode")
    GenericListResponseDTO<ResponseUserDTO> findUserAll(@Param("auth") String p_BearerToken);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/getPhoneNumberEmailByUserId")
    ResponsePhoneNumberEmail getPhoneNumberEmailByUserId(@Param("auth") String p_BearerToken, RequestFindByIdDTO p_RequestFindByIdDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findVolunteerByCodeContainingAndNameContaining")
    List<ResponseUserDTO> findVolunteerByCodeContainingAndNameContaining(@Param("auth") String p_BearerToken, RequestFindByCodeNameDTO p_RequestFindByCodeName);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("GET /secured/user/findAllVolunteer")
    List<ResponseUserDTO> findAllVolunteer(@Param("auth") String p_BearerToken);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findVolunteerByCodeContaining")
    List<ResponseUserDTO> findVolunteerByCodeContaining(@Param("auth") String p_BearerToken, RequestFindByCodeDTO p_RequestFindByCodeDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findVolunteerByNameContaining")
    List<ResponseUserDTO> findVolunteerByNameContaining(@Param("auth") String p_BearerToken, RequestFindByNameDTO p_requestFindByNameDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/insertUserAgent")
    ResponseUserDTO insertUserAgent(@Param("auth") String p_BearerToken, RequestInsertUserAgentDTO p_RequestInsertUserAgentDTO);

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("POST /secured/user/findAgentByUserName")
    ResponseUserDTO findAgentByUserName(@Param("auth") String p_BearerToken, RequestFindByCodeDTO p_RequestFindByCodeDTO);
}
