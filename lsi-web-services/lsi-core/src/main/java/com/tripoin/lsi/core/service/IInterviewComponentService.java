package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestInsertComponentDTO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewComponentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDetailComponentDTO;
import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewComponentService extends IScaffoldingService<InterviewComponent, RequestInterviewComponentDTO> {

    List<InterviewComponent> findByInterviewEventId(Long p_InterviewEventId) throws ServiceException;

    List<InterviewComponent> findByInterviewEventIdOrderByComponentOrder(Long p_InterviewEventId) throws ServiceException;

    List<ResponseDetailComponentDTO> findComponentDetailByEventId(Long p_InterviewEventId) throws ServiceException;

    @Transactional
    ResponseData mergeComponentCompletely(RequestInsertComponentDTO p_RequestInsertComponentDTO) throws ServiceException;
}
