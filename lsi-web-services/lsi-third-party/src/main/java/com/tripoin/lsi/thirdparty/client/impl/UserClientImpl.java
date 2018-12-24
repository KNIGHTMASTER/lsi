package com.tripoin.lsi.thirdparty.client.impl;

import com.tripoin.lsi.thirdparty.client.IUserClient;
import com.wissensalt.feign.FeignBuilderFactory;
import com.wissensalt.shared.dto.request.*;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class UserClientImpl {

    @Value("${feign.acl.api.base-path}")
    private String aclApiBasePath;

    public GenericSingleDATAResponseDTO<ResponseUserDTO> findByUserId(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findUserById(p_Bearer, p_RequestFindByIdDTO);
    }

    public GenericSingleDATAResponseDTO<ResponseUserDTO> findByUserName(String p_Bearer, String p_UserName) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        RequestFindByCodeDTO requestFindByCodeDTO = new RequestFindByCodeDTO();
        requestFindByCodeDTO.setCode(p_UserName);
        return userClient.findUserByUserName(p_Bearer, requestFindByCodeDTO);
    }

    public GenericListResponseDTO<ResponseUserDTO> findAllUser(String p_Bearer) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findUserAll(p_Bearer);
    }

    public ResponsePhoneNumberEmail getPhoneNumberEmail(String p_Bearer, RequestFindByIdDTO p_RequestFindByIdDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.getPhoneNumberEmailByUserId(p_Bearer, p_RequestFindByIdDTO);
    }

    public List<ResponseUserDTO> findVolunteerByCodeContainingAndNameContaining(String p_Bearer, RequestFindByCodeNameDTO p_RequestFindByCodeNameDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findVolunteerByCodeContainingAndNameContaining(p_Bearer, p_RequestFindByCodeNameDTO);
    }

    public List<ResponseUserDTO> findAllVolunteer(String p_Bearer) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findAllVolunteer(p_Bearer);
    }

    public List<ResponseUserDTO> findVolunteerByCodeContaining(String p_Bearer, RequestFindByCodeDTO p_RequestFindByCodeDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findVolunteerByCodeContaining(p_Bearer, p_RequestFindByCodeDTO);
    }

    public List<ResponseUserDTO> findVolunteerByNameContaining(String p_Bearer, RequestFindByNameDTO p_RequestFindByNameDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findVolunteerByNameContaining(p_Bearer, p_RequestFindByNameDTO);
    }

    public ResponseUserDTO insertUserAgent(String p_Bearer, RequestInsertUserAgentDTO p_RequestInsertUserAgentDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.insertUserAgent(p_Bearer, p_RequestInsertUserAgentDTO);
    }

    public ResponseUserDTO findAgentByUserName(String p_Bearer, RequestFindByCodeDTO p_RequestFindByCodeDTO) {
        IUserClient userClient = FeignBuilderFactory.createClient(IUserClient.class, aclApiBasePath);
        return userClient.findAgentByUserName(p_Bearer, p_RequestFindByCodeDTO);
    }
}
