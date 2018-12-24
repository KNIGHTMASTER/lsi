package com.tripoin.lsi.core.dao;

import com.tripoin.lsi.core.data.model.InterviewComponent;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewComponentDAO extends IScaffoldingDAO<InterviewComponent> {

    List<InterviewComponent> findByInterviewEvent_id(Long p_InterviewEventId) throws DAOException;

    List<InterviewComponent> findByInterviewEvent_idOrderByComponentOrder(Long p_InterviewEventId) throws DAOException;
}
