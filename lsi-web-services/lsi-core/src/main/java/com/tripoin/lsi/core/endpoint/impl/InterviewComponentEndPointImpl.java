package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInsertComponentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewComponentDTO;
import com.tripoin.lsi.core.data.mapper.InterviewComponentMapper;
import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.tripoin.lsi.core.endpoint.IInterviewComponentEndPoint;
import com.tripoin.lsi.core.service.IInterviewComponentService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewComponentEndPointImpl extends AScaffoldingEndPoint<InterviewComponent, RequestInterviewComponentDTO, ResponseInterviewComponentDTO> implements IInterviewComponentEndPoint {

    @Autowired
    private IInterviewComponentService interviewComponentService;

    @Autowired
    private InterviewComponentMapper interviewComponentMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewComponentEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        dataMapperIntegration = interviewComponentMapper;
        scaffoldingService = interviewComponentService;
    }

    @RequestLogger(name = "Interview Component | Find Detail By Event Id")
    @Override
    public List<ResponseDetailComponentDTO> findComponentDetailByEventId(HttpServletRequest httpServletRequest, @RequestBody RequestFindByIdDTO p_RequestFindByIdDTO) throws EndPointException {
        try {
            return interviewComponentService.findComponentDetailByEventId(p_RequestFindByIdDTO.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error Find Component By Event Id {}", e.toString());
            return null;
        }
    }

    @RequestLogger(name = "Interview Component | Insert Component Completely")
    @Override
    public ResponseData insertComponentCompletely(HttpServletRequest httpServletRequest, @RequestBody RequestInsertComponentDTO p_RequestInsertComponentDTO) throws EndPointException {
        try {
            return interviewComponentService.mergeComponentCompletely(p_RequestInsertComponentDTO);
        } catch (ServiceException e) {
            LOGGER.error("Error Insert Component Completely {}", e.toString());
            return null;
        }
    }
}
