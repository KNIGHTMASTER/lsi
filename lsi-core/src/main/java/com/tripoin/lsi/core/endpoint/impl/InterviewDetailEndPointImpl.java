package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.tripoin.lsi.core.data.mapper.InterviewDetailMapper;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.tripoin.lsi.core.endpoint.IInterviewDetailEndPoint;
import com.tripoin.lsi.core.service.IInterviewDetailService;
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
public class InterviewDetailEndPointImpl extends AScaffoldingEndPoint<InterviewDetail, RequestInterviewDetailDTO, ResponseInterviewDetailDTO> implements IInterviewDetailEndPoint {

    @Autowired
    private IInterviewDetailService interviewDetailService;

    @Autowired
    private InterviewDetailMapper interviewDetailMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = interviewDetailService;
        dataMapperIntegration = interviewDetailMapper;
    }
}
