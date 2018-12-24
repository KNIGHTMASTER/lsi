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
@Table(name = "mst_interview_component")
public class InterviewComponent extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = -1755158686380250494L;

    @Column(name = "component_order")
    private Integer componentOrder;

    @Column(name = "component_type")
    private String componentType;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_event_id")
    private InterviewEvent interviewEvent;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "interviewComponent")
    private Question question;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "interviewComponent")
    private StatementImage statementImage;

}
