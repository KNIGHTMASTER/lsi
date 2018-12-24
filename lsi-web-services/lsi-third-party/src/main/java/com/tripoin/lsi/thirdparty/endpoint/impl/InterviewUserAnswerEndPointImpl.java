package com.tripoin.lsi.thirdparty.endpoint.impl;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.mapper.InterviewUserAnswerMapper;
import com.tripoin.lsi.thirdparty.data.model.InterviewUserAnswer;
import com.tripoin.lsi.thirdparty.endpoint.IInterviewUserAnswerEndPoint;
import com.tripoin.lsi.thirdparty.service.IInterviewUserAnswerService;
import com.tripoin.lsi.thirdparty.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewUserAnswerEndPointImpl extends AScaffoldingEndPoint<InterviewUserAnswer, RequestInterviewUserAnswerDTO, ResponseInterviewUserAnswerDTO> implements IInterviewUserAnswerEndPoint {

    @Autowired
    private IInterviewUserAnswerService answerService;

    @Autowired
    private InterviewUserAnswerMapper interviewUserAnswerMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = answerService;
        dataMapperIntegration = interviewUserAnswerMapper;
    }

    @RequestLogger(name = "Interview-user-answer|find-summary-user-answer")
    @Override
    public Page<ResponseSummaryUserAnswerDTO> findSummaryUserAnswerDTO(HttpServletRequest p_HttpServletRequest, Principal p_Principal, @RequestBody RequestSummaryUserAnswerDTO p_RequestSummaryUserAnswerDTO) throws EndPointException {
        String bearer = HttpUtil.getHeaderAuthorization(p_HttpServletRequest);
        String principal = p_Principal.getName();
        try {
            return answerService.findSummaryUserAnswer(bearer, principal, p_RequestSummaryUserAnswerDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Find Summary User Answer {}", e.toString());
            return null;
        }
    }
}
