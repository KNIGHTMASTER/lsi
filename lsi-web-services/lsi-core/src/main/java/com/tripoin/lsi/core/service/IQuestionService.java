package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionDTO;
import com.tripoin.lsi.core.data.model.Question;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IQuestionService extends IScaffoldingService<Question, RequestQuestionDTO> {

    List<Question> findByComponentId(Long p_ComponentId) throws ServiceException;
}
