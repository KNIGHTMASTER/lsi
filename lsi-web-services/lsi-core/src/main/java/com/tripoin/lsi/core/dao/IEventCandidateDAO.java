package com.tripoin.lsi.core.dao;

import com.tripoin.lsi.core.data.model.EventCandidate;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IEventCandidateDAO extends IScaffoldingDAO<EventCandidate> {

    List<EventCandidate> findByInterviewEvent_id(Long p_InterviewEventId) throws DAOException;

    @Query("SELECT e.id FROM EventCandidate e JOIN e.interviewEvent v WHERE e.id NOT IN ?1 and v.id = ?2")
    List<Long> findNotIn(List<Long> p_Ids, Long p_EventId) throws DAOException;

    EventCandidate findByInterviewEvent_idAndCandidateOrderNumber(Long p_InterviewEventId, Integer p_candidateOrderNumber) throws DAOException;
}
