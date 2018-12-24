package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.dao.IInterviewImageDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewImageDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewDetail;
import com.tripoin.lsi.thirdparty.data.model.InterviewImage;
import com.tripoin.lsi.thirdparty.service.IInterviewDetailService;
import com.tripoin.lsi.thirdparty.service.IInterviewImageService;
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
public class InterviewImageServiceImpl extends AScaffoldingService<InterviewImage, RequestInterviewImageDTO> implements IInterviewImageService {

    @Autowired
    private IInterviewImageDAO interviewImageDAO;

    @Autowired
    private IInterviewDetailService interviewDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewImageServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewImageDAO;
    }

    @Override
    public InterviewImage generateEntityForCommonTrx(RequestInterviewImageDTO requestInterviewImageDTO) throws ServiceException {
        InterviewImage interviewImage = new InterviewImage();
        interviewImage = matchBaseFields(interviewImage, requestInterviewImageDTO);
        try {
            interviewImage.setUploadTimeStamp(FormatDateConstant.DEFAULT.parse(requestInterviewImageDTO.getUploadTimeStamp()));
        } catch (ParseException e) {
            LOGGER.error("error parsing timestamp image {}", e.toString());
        }
        interviewImage.setFileType(requestInterviewImageDTO.getFileType());
        interviewImage.setFileSize(requestInterviewImageDTO.getFileSize());
        interviewImage.setFilePath(requestInterviewImageDTO.getFilePath());
        interviewImage.setLatitude(requestInterviewImageDTO.getLatitude());
        interviewImage.setLongitude(requestInterviewImageDTO.getLongitude());
        if (interviewImage.getInterviewDetail() != null) {
            InterviewDetail interviewDetail = interviewDetailService.findById(requestInterviewImageDTO.getInterviewDetailId());
            if (interviewDetail != null) {
                interviewImage.setInterviewDetail(interviewDetail);
            }
        }
        return interviewImage;
    }

    @Override
    public Integer insertThroughMessaging(RequestInterviewImageDTO p_RequestInterviewImageDTO) throws ServiceException {
        InterviewImage  interviewImage = generateEntityForCommonTrx(p_RequestInterviewImageDTO);
        Integer result = 1;
        if (interviewImage != null) {
            insert(interviewImage);
        }else {
            result = 0;
        }
        return result;
    }
}
