package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseQuestionDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Question End Point", value = "Api Scaffolding Question", description = "Api Scaffolding for Entity Question")
@RequestMapping("/secured/question")
public interface IQuestionEndPoint extends IScaffoldingEndPoint<RequestQuestionDTO, ResponseQuestionDTO> {
}
