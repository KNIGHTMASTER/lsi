package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestAddressDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.master.ResponseAddressDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Address End Point", value = "Api Scaffolding Address", description = "Api Scaffolding for Entity Address")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.ADDRESS)
public interface IAddressEndPoint extends IScaffoldingEndPoint<RequestAddressDTO, ResponseAddressDTO>{
}
