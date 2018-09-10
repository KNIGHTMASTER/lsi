package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewHeaderDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewHeaderDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Interview Header End Point", value = "Api Scaffolding Interview Header", description = "Api Scaffolding for Entity Interview Header")
@RequestMapping("/secured/interviewHeader")
public interface IInterviewHeaderEndPoint extends IScaffoldingEndPoint<RequestInterviewHeaderDTO, ResponseInterviewHeaderDTO> {
}
