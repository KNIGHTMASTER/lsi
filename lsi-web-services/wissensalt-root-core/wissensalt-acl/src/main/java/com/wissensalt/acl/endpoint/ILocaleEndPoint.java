package com.wissensalt.acl.endpoint;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestLocaleDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.master.ResponseLocaleDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 8/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Locale End Point", value = "Api Scaffolding Locale", description = "Api Scaffolding for Entity Locale")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.LOCALE)
public interface ILocaleEndPoint extends IScaffoldingEndPoint<RequestLocaleDTO, ResponseLocaleDTO> {
}
