package com.tripoin.lsi.thirdparty.dao;

import com.tripoin.lsi.thirdparty.data.model.InterviewUserAnswer;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewUserAnswerDAO extends IScaffoldingDAO<InterviewUserAnswer> {

    List<InterviewUserAnswer> findByInterviewQuestionId(Long p_QuestionId) throws DAOException;

    InterviewUserAnswer findByInterviewQuestionIdAndInterviewDetail_id(Long p_QuestionId, Long p_InterviewDetailId) throws DAOException;

    List<InterviewUserAnswer> findByInterviewDetail_idAndQuestionAnswerIdNotNull(Long p_DetailId) throws DAOException;

    @Query(value = "select ih.village_id as village_id, q.name as question, qa.name from trx_interview_user_answer ua join trx_interview_detail id on ua.interview_detail_id = id.id join trx_interview_header ih on  ih.id=id.interview_header_id join mst_question q on ua.interview_question_id = q.id join mst_question_answer qa on qa.id = ua.question_answer_id where ih.id in (?1)", nativeQuery = true)
    List<Object[]> findSummaryUserAnswerByHeaders(Long[] interviewHeaderIds) throws DAOException;
}
