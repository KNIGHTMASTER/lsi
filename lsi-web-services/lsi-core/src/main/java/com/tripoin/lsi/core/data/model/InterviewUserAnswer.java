package com.tripoin.lsi.core.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "trx_interview_user_answer")
public class InterviewUserAnswer extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 3177664694127940770L;

    @Column(name = "interview_question_id")
    private Long interviewQuestionId;

    @Column(name = "question_answer_id")
    private Long questionAnswerId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_detail_id")
    private InterviewDetail interviewDetail;

    @Column(name = "other_answer")
    private String otherAnswer;
}
