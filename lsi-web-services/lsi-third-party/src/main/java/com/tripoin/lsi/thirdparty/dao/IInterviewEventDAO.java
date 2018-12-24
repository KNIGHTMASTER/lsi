package com.tripoin.lsi.thirdparty.dao;

import com.tripoin.lsi.thirdparty.data.model.InterviewEvent;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewEventDAO extends IScaffoldingDAO<InterviewEvent> {

    @Query("SELECT id, name FROM InterviewEvent a WHERE a.eventStartDate <= ?1 AND a.eventEndDate >= ?1")
    List<InterviewEvent> findLOVEventActive(Date p_NewDate) throws DAOException;
}
