package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseEventCandidateDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Event Candidate End Point", value = "Api Event Candidate", description = "Api Event Candidate")
@RequestMapping("/secured/eventCandidate")
public interface IEventCandidateEndPoint extends IScaffoldingEndPoint<RequestEventCandidateDTO, ResponseEventCandidateDTO> {
}
