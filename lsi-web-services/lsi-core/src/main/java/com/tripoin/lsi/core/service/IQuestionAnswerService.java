package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionAnswerDTO;
import com.tripoin.lsi.core.data.model.QuestionAnswer;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingService;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IQuestionAnswerService extends IScaffoldingService<QuestionAnswer, RequestQuestionAnswerDTO> {

    List<QuestionAnswer> findByQuestionIdOrderByName(Long p_QuestionId) throws ServiceException;

}
