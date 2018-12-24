package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IStatementImageDAO;
import com.tripoin.lsi.core.data.dto.request.RequestStatementImageDTO;
import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.tripoin.lsi.core.data.model.StatementImage;
import com.tripoin.lsi.core.service.IInterviewComponentService;
import com.tripoin.lsi.core.service.IStatementImageService;
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
public class StatementImageServiceImpl extends AScaffoldingService<StatementImage, RequestStatementImageDTO> implements IStatementImageService {

    @Autowired
    private IStatementImageDAO statementImageDAO;

    @Autowired
    private IInterviewComponentService interviewComponentService;

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = statementImageDAO;
    }

    @Override
    public StatementImage generateEntityForCommonTrx(RequestStatementImageDTO p_RequestStatementImageDTO) throws ServiceException {
        StatementImage result = new StatementImage();
        result = matchBaseFields(result, p_RequestStatementImageDTO);
        if (p_RequestStatementImageDTO.getInterviewComponentId() != null) {
            InterviewComponent interviewComponent = interviewComponentService.findById(p_RequestStatementImageDTO.getInterviewComponentId());
            if (interviewComponent != null) {
                result.setInterviewComponent(interviewComponent);
            }
        }
        return result;
    }


}
