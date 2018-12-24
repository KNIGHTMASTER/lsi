package com.tripoin.lsi.core.endpoint;

import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 7/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Registration", value = "API Registration", description = "Rest Web Service for Handling Registration")
public interface IRegistrationEndPoint {

    /*@ApiOperation(value = "Register New User")
    @PostMapping(ACLConstant.Path.REGISTER_USER)*/
    ResponseData registerNewUser() throws EndPointException;

    @ApiOperation(value = "Register Bulk User")
    @PostMapping("/secured/registerBulkUser")
    ResponseData registerBulkUser(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile p_MultiPartFile) throws EndPointException;

    @ApiOperation(value = "Register Bulk User")
    @PostMapping("/secured/registerBulkArea")
    ResponseData registerBulkArea(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile p_MultiPartFile) throws EndPointException;
}
