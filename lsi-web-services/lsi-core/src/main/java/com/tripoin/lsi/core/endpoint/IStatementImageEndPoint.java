package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestStatementImageDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseStatementImageDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Statement Image End Point", value = "Api Scaffolding Statement Image", description = "Api Scaffolding for Entity Statement Image")
@RequestMapping("/secured/statementImage")
public interface IStatementImageEndPoint extends IScaffoldingEndPoint<RequestStatementImageDTO, ResponseStatementImageDTO> {
}
