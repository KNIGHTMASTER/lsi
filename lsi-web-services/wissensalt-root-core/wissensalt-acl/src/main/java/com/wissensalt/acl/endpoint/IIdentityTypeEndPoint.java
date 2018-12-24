package com.wissensalt.acl.endpoint;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestIdentityTypeDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.master.ResponseIdentityTypeDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 8/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Identity Type End Point", value = "Api Scaffolding Identity Type", description = "Api Scaffolding for Entity Identity Type")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.IDENTITY_TYPE)
public interface IIdentityTypeEndPoint extends IScaffoldingEndPoint<RequestIdentityTypeDTO, ResponseIdentityTypeDTO> {
}
