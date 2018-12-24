package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.endpoint.IRegistrationEndPoint;
import com.tripoin.lsi.core.service.IRegistrationService;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.CommonConstant;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class RegistrationEndPointImpl implements IRegistrationEndPoint {

    @Autowired
    private IRegistrationService registrationService;

    @Override
    public ResponseData registerNewUser() throws EndPointException {
        return null;
    }

    @RequestLogger(name = "Registration|Register Bulk User")
    @Override
    public ResponseData registerBulkUser(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile p_MultiPartFile) throws EndPointException {
        ResponseData responseData;
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            responseData = registrationService.registerBulkUser(bearer, p_MultiPartFile);
        } catch (ServiceException e) {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @RequestLogger(name = "Registration|Register Bulk Area")
    @Override
    public ResponseData registerBulkArea(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile p_MultiPartFile) throws EndPointException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        try {
            int result = registrationService.registerBulkArea(p_MultiPartFile);
            if (result != CommonConstant.GeneralValue.ONE) {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
            }
        } catch (ServiceException e) {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }
}
