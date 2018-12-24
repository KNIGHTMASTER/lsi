package com.tripoin.lsi.core.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "mst_question")
public class Question extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = -7173245954062700868L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_component_id")
    private InterviewComponent interviewComponent;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "question")
    private Set<QuestionAnswer> questionAnswers;

    @Column(name = "question_type")
    private String questionType;
}
