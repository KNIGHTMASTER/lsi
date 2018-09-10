package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Detail End Point", value = "Api Scaffolding Interview Detail", description = "Api Scaffolding for Entity Interview Detail")
@RequestMapping("/secured/interviewDetail")
public interface IInterviewDetailEndPoint extends IScaffoldingEndPoint<RequestInterviewDetailDTO, ResponseInterviewDetailDTO> {
}
