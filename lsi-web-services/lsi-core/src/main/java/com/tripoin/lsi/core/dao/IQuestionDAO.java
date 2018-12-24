package com.tripoin.lsi.core.dao;

import com.tripoin.lsi.core.data.model.Question;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IQuestionDAO extends IScaffoldingDAO<Question> {

    List<Question> findByInterviewComponent_id(Long p_InterviewComponentId) throws DAOException;
}
