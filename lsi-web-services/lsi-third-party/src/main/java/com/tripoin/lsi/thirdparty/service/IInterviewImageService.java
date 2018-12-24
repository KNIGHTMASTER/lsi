package com.tripoin.lsi.thirdparty.service;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewImageDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewImage;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewImageService extends IScaffoldingService<InterviewImage, RequestInterviewImageDTO> {

    Integer insertThroughMessaging(RequestInterviewImageDTO p_RequestInterviewImageDTO) throws ServiceException;
}
