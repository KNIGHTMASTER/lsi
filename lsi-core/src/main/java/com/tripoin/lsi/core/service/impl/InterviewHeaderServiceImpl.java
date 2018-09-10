package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IInterviewHeaderDAO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewHeaderDTO;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewHeaderServiceImpl extends AScaffoldingService<InterviewHeader, RequestInterviewHeaderDTO> implements IInterviewHeaderService {

    @Autowired
    private IInterviewHeaderDAO interviewHeaderDAO;

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewHeaderDAO;
    }

    @Override
    public InterviewHeader generateEntityForCommonTrx(RequestInterviewHeaderDTO p_RequestInterviewHeaderDTO) throws ServiceException {
        InterviewHeader interviewHeader = new InterviewHeader();
        interviewHeader = matchBaseFields(interviewHeader, p_RequestInterviewHeaderDTO);
        if (p_RequestInterviewHeaderDTO.getProvinceId() != null) {
            interviewHeader.setProvinceId(p_RequestInterviewHeaderDTO.getProvinceId());
        }
        if (p_RequestInterviewHeaderDTO.getCityId() != null) {
            interviewHeader.setCityId(p_RequestInterviewHeaderDTO.getCityId());
        }
        if (p_RequestInterviewHeaderDTO.getDistrictId() != null) {
            interviewHeader.setDistrictId(p_RequestInterviewHeaderDTO.getDistrictId());
        }
        if (p_RequestInterviewHeaderDTO.getVillageId() != null) {
            interviewHeader.setVillageId(p_RequestInterviewHeaderDTO.getVillageId());
        }
        return interviewHeader;
    }

}
