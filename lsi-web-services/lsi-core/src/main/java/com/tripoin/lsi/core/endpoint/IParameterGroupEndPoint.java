package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestParameterGroupDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseParameterGroupDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Parameter Group End Point", value = "Api Scaffolding Parameter Group", description = "Api Scaffolding for Entity Parameter Group")
@RequestMapping("/secured/parameterGroup")
public interface IParameterGroupEndPoint extends IScaffoldingEndPoint<RequestParameterGroupDTO, ResponseParameterGroupDTO> {
}
