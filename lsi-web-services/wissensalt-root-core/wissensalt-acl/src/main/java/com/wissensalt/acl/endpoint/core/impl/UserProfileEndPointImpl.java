package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.request.RequestResetPasswordDTO;
import com.wissensalt.shared.dto.request.RequestUpdatePasswordDTO;
import com.wissensalt.shared.dto.request.RequestUpdateProfileDTO;
import com.wissensalt.shared.dto.response.master.*;
import com.wissensalt.acl.endpoint.core.IUserProfileEndPoint;
import com.wissensalt.scaffolding.dto.request.RequestUserProfileDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.IUserProfileService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.security.ResponseUserProfileDTO;
import com.wissensalt.shared.entity.mapper.security.UserProfileMapper;
import com.wissensalt.shared.entity.security.SecurityUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class UserProfileEndPointImpl extends AScaffoldingEndPoint<SecurityUserProfile, RequestUserProfileDTO, ResponseUserProfileDTO> implements IUserProfileEndPoint {

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = userProfileService;
        dataMapperIntegration = userProfileMapper;
    }

    @RequestLogger(name = "user-profile|get-profile")
    @Override
    public ResponseProfileDTO getProfile(Principal p_Principal) {
        ResponseProfileDTO<ResponseAddressDTO, ResponseContactDTO> profileResponseDTO = new ResponseProfileDTO<>();
        try{
            profileResponseDTO = userProfileService.getProfile(p_Principal.getName());
            profileResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_GET_PROFILE_SUCCESS.getResponseCode(), EResponseCode.RC_GET_PROFILE_SUCCESS.getResponseMsg()));
        }catch (Exception e){
            LOGGER.error("Cannot get profile {}", e.toString());
            profileResponseDTO.setResponseData(new ResponseData(EResponseCode.RC_GET_PROFILE_FAILED.getResponseCode(), EResponseCode.RC_GET_PROFILE_FAILED.getResponseMsg()));
        }
        return profileResponseDTO;
    }

    @RequestLogger(name = "user-profile|update-password")
    @Override
    public ResponseData updatePassword(@RequestBody RequestUpdatePasswordDTO p_RequestUpdatePasswordDTO) {
        ResponseData responseData;
        try {
            responseData =  userProfileService.updatePassword(p_RequestUpdatePasswordDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Update Password {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_UPDATE_PASSWORD_FAILED.getResponseCode(), EResponseCode.RC_UPDATE_PASSWORD_FAILED.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "user-profile|update-profile")
    @Override
    public ResponseData updateProfile(@RequestBody RequestUpdateProfileDTO p_RequestUpdateProfileDTO) {
        ResponseData responseData;
        try {
            responseData = userProfileService.updateProfile(p_RequestUpdateProfileDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Update Profile {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_GET_PROFILE_FAILED.getResponseCode(), EResponseCode.RC_GET_PROFILE_FAILED.getResponseMsg());
        }
        return responseData;
    }

    @Override
    public ResponseData resetPassword(@RequestBody RequestResetPasswordDTO p_RequestResetPasswordDTO) {
        ResponseData responseData;
        try {
            responseData = userProfileService.resetPassword(p_RequestResetPasswordDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Reset Password : {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @Override
    public ResponseData kickUser(@RequestBody RequestResetPasswordDTO p_RequestResetPasswordDTO) {
        ResponseData responseData;
        try {
            responseData = userProfileService.kickUser(p_RequestResetPasswordDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Kick User : {}", e.toString());
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }
}
