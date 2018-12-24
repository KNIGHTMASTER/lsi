package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestFunctionAssignmentDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.security.ResponseFunctionAssignmentDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Function Assignment End Point", value = "Api Scaffolding Function Assignment", description = "Api Scaffolding for Entity Function Assignment")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.FUNCTION_ASSIGNMENT)
public interface IFunctionAssignmentEndPoint extends IScaffoldingEndPoint<RequestFunctionAssignmentDTO, ResponseFunctionAssignmentDTO> {
}
