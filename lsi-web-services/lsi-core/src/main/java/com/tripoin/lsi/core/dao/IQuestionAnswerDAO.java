package com.tripoin.lsi.core.dao;

import com.tripoin.lsi.core.data.model.QuestionAnswer;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IQuestionAnswerDAO extends IScaffoldingDAO<QuestionAnswer> {

    List<QuestionAnswer> findByQuestion_IdOrderByName(Long p_QuestionId) throws DAOException;

}
