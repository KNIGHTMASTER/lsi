package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IInterviewDetailDAO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewDetailDTO;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.service.IInterviewDetailService;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewDetailServiceImpl extends AScaffoldingService<InterviewDetail, RequestInterviewDetailDTO> implements IInterviewDetailService {

    @Autowired
    private IInterviewDetailDAO interviewDetailDAO;

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewDetailServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewDetailDAO;
    }

    @Override
    public InterviewDetail generateEntityForCommonTrx(RequestInterviewDetailDTO p_RequestInterviewDetailDTO) throws ServiceException {
        InterviewDetail interviewDetail = new InterviewDetail();
        interviewDetail = matchBaseFields(interviewDetail, p_RequestInterviewDetailDTO);
        interviewDetail.setLatitude(p_RequestInterviewDetailDTO.getLatitude());
        interviewDetail.setLongitude(p_RequestInterviewDetailDTO.getLongitude());
        interviewDetail.setInterviewStatus(p_RequestInterviewDetailDTO.getInterviewStatus());
        interviewDetail.setRespondentName(p_RequestInterviewDetailDTO.getRespondentName());
        interviewDetail.setRespondentAge(p_RequestInterviewDetailDTO.getRespondentAge());
        interviewDetail.setRespondentReligion(p_RequestInterviewDetailDTO.getRespondentReligion());
        try {
            interviewDetail.setInterviewTimeStamp(FormatDateConstant.DEFAULT.parse(p_RequestInterviewDetailDTO.getInterviewTimeStamp()));
        } catch (ParseException e) {
            LOGGER.error("Error Parsing Date {} ", e.toString());
        }
        if (p_RequestInterviewDetailDTO.getInterviewHeaderId() != null) {
            InterviewHeader interviewHeader = interviewHeaderService.findById(p_RequestInterviewDetailDTO.getInterviewHeaderId());
            interviewDetail.setInterviewHeader(interviewHeader);
        }
        return interviewDetail;
    }
}
