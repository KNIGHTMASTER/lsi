package com.wissensalt.security.oauth2.client;

import com.wissensalt.shared.dto.response.base.ResponseNameDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_ACCEPT_JSON;
import static com.wissensalt.shared.constant.CommonConstant.Security.HEADER_CONTENT_TYPE;

/**
 * Created on 10/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IUserClient {

    @Headers({HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE, "Authorization: {auth}"})
    @RequestLine("GET /secured/user/getPrincipal")
    ResponseNameDTO getPrincipal(@Param("auth") String p_BearerToken);
}
