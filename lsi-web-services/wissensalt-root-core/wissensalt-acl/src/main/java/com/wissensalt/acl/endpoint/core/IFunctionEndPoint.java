package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestFunctionDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.security.ResponseFunctionDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Function End Point", value = "Api Scaffolding Function", description = "Api Scaffolding for Entity Function")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.FUNCTION)
public interface IFunctionEndPoint extends IScaffoldingEndPoint<RequestFunctionDTO, ResponseFunctionDTO> {
}
