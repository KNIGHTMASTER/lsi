package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestEventCandidateDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseEventCandidateDTO;
import com.tripoin.lsi.core.data.mapper.EventCandidateMapper;
import com.tripoin.lsi.core.data.model.EventCandidate;
import com.tripoin.lsi.core.endpoint.IEventCandidateEndPoint;
import com.tripoin.lsi.core.service.IEventCandidateService;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class EventCandidateEndPointImpl extends AScaffoldingEndPoint<EventCandidate, RequestEventCandidateDTO, ResponseEventCandidateDTO> implements IEventCandidateEndPoint {

    @Autowired
    private IEventCandidateService eventCandidateService;

    @Autowired
    private EventCandidateMapper eventCandidateMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = eventCandidateService;
        dataMapperIntegration = eventCandidateMapper;
    }
}
