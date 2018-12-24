package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestUserProfileDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestResetPasswordDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.request.RequestUpdatePasswordDTO;
import com.wissensalt.shared.dto.request.RequestUpdateProfileDTO;
import com.wissensalt.shared.dto.response.master.ResponseProfileDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserProfileDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "User Profile End Point", value = "Api Scaffolding User Profile", description = "Api Scaffolding for Entity User Profile")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.USER_PROFILE)
public interface IUserProfileEndPoint extends IScaffoldingEndPoint<RequestUserProfileDTO, ResponseUserProfileDTO> {

    @GetMapping(name = "/getProfile")
    ResponseProfileDTO getProfile(Principal p_Principal);

    @PostMapping(name = "/updateProfile")
    ResponseData updateProfile(@RequestBody RequestUpdateProfileDTO p_RequestUpdateProfileDTO);

    @PutMapping("/updatePasword")
    ResponseData updatePassword(@RequestBody RequestUpdatePasswordDTO p_RequestUpdatePasswordDTO);

    @PostMapping("/resetPassword")
    ResponseData resetPassword(@RequestBody RequestResetPasswordDTO p_RequestResetPasswordDTO);

    @PostMapping("/kickUser")
    ResponseData kickUser(@RequestBody RequestResetPasswordDTO p_RequestResetPasswordDTO);
}
