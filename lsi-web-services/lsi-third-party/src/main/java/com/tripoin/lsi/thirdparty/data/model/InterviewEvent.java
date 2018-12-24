package com.tripoin.lsi.thirdparty.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "trx_interview_event")
public class InterviewEvent extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8855759735353395953L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_start_date")
    private Date eventStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_end_date")
    private Date eventEndDate;

    /**
     * 1 : Enabled || 0 : Disabled
     */
    @Column(name = "is_vote")
    private Integer isVote;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "interviewEvent", orphanRemoval = true)
    private Set<InterviewHeader> interviewHeaders;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "interviewEvent", orphanRemoval = true)
    private Set<InterviewComponent> interviewComponents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "interviewEvent", orphanRemoval = true)
    private Set<EventCandidate> eventCandidates;
}
