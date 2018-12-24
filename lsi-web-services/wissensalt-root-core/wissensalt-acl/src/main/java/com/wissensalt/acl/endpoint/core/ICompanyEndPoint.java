package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestCompanyDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.security.ResponseCompanyDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Company End Point", value = "Api Scaffolding Company", description = "Api Scaffolding for Entity Company")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.COMPANY)
public interface ICompanyEndPoint extends IScaffoldingEndPoint<RequestCompanyDTO, ResponseCompanyDTO> {
}
