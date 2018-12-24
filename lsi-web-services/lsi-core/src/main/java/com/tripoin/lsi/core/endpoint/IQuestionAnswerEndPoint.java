package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionAnswerDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseQuestionAnswerDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Question Answer End Point", value = "Api Scaffolding Question Answer", description = "Api Scaffolding for Entity Question Answer")
@RequestMapping("/secured/questionAnswer")
public interface IQuestionAnswerEndPoint extends IScaffoldingEndPoint<RequestQuestionAnswerDTO, ResponseQuestionAnswerDTO> {
}
