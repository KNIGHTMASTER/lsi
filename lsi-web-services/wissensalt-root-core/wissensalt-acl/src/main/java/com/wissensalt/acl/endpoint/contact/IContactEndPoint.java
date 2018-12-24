package com.wissensalt.acl.endpoint.contact;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestContactDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.master.ResponseContactDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Contact End Point", value = "Api Scaffolding Contact", description = "Api Scaffolding for Entity Contact")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.CONTACT)
public interface IContactEndPoint extends IScaffoldingEndPoint<RequestContactDTO, ResponseContactDTO> {
}
