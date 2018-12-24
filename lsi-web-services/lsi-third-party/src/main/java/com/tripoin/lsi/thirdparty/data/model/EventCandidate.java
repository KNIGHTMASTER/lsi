package com.tripoin.lsi.thirdparty.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "trx_event_candidate")
public class EventCandidate extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 4646720918057940252L;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_event_id")
    private InterviewEvent interviewEvent;

    @Column(name = "candicate_image_path")
    private String candidateImagePath;

    @Column(name = "candidate_order_number")
    private Integer candidateOrderNumber;
}
