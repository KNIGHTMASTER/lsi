package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewImageDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewImageDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.base.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Image End Point", value = "Api Scaffolding Interview Image", description = "Api Scaffolding for Entity Interview Image")
@RequestMapping("/secured/interviewImage")
public interface IInterviewImageEndPoint extends IScaffoldingEndPoint<RequestInterviewImageDTO, ResponseInterviewImageDTO> {

    @PostMapping("/insertThroughMessaging")
    ResponseData insertThroughMessaging(HttpServletRequest httpServletRequest, @RequestBody RequestInterviewImageDTO requestInterviewImageDTO) throws EndPointException;
}
