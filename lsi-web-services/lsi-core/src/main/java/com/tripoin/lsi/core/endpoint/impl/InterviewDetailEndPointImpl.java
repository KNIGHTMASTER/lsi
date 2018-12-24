package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailByHeaderDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailByHeaderDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewDetailDTO;
import com.tripoin.lsi.core.data.mapper.InterviewDetailMapper;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.tripoin.lsi.core.endpoint.IInterviewDetailEndPoint;
import com.tripoin.lsi.core.service.IInterviewDetailService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @RequestLogger(name = "Interview Detail|find-by-header-id")
    @Override
    public Page<ResponseInterviewDetailByHeaderDTO> findByHeaderId(HttpServletRequest httpServletRequest, @RequestBody RequestInterviewDetailByHeaderDTO p_RequestInterviewDetailByHeaderDTO) throws EndPointException {
        try {
            List<ResponseInterviewDetailByHeaderDTO> result = new ArrayList<>();
            PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestInterviewDetailByHeaderDTO);
            Page<InterviewDetail> interviewDetails = interviewDetailService.findByInterviewHeaderId(p_RequestInterviewDetailByHeaderDTO.getId(), pageRequest);
            for (InterviewDetail interviewDetail : interviewDetails) {
                result.add(interviewDetailMapper.convertDetailByHeader(interviewDetail));
            }
            return new PageImpl(result, pageRequest, interviewDetails.getTotalElements());
        } catch (ServiceException e) {
            LOGGER.error("Error Find By Header Id {}", e.toString());
            return null;
        }
    }
}
