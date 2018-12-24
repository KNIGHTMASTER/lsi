package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestCountryDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.master.ResponseCountryDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Country End Point", value = "Api Scaffolding Country", description = "Api Scaffolding for Entity Country")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.COUNTRY)
public interface ICountryEndPoint extends IScaffoldingEndPoint<RequestCountryDTO, ResponseCountryDTO> {
}
