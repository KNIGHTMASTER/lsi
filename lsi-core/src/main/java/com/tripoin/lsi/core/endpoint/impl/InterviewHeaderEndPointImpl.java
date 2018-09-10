package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewHeaderDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewHeaderDTO;
import com.tripoin.lsi.core.data.mapper.InterviewHeaderMapper;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.endpoint.IInterviewHeaderEndPoint;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewHeaderEndPointImpl extends AScaffoldingEndPoint<InterviewHeader, RequestInterviewHeaderDTO, ResponseInterviewHeaderDTO> implements IInterviewHeaderEndPoint {

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private InterviewHeaderMapper interviewHeaderMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = interviewHeaderService;
        dataMapperIntegration = interviewHeaderMapper;
    }
}
