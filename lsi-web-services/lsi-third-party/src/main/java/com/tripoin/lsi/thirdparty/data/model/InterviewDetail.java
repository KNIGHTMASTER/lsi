package com.tripoin.lsi.thirdparty.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "trx_interview_detail")
public class InterviewDetail extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5807358504761502273L;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "interview_status")
    private String interviewStatus;

    @Column(name = "respondent_name")
    private String respondentName;

    @Column(name = "respondent_age")
    private Integer respondentAge;

    @Column(name = "respondent_religion")
    private String respondentReligion;

    @Column(name = "respondent_address")
    private String respondentAddress;

    @Column(name = "respondent_phone_number")
    private String respondentPhoneNumber;

    @Column(name = "interview_start_time")
    private Date interviewStartTime;

    @Column(name = "interview_end_time")
    private Date interviewEndTime;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_header_id")
    private InterviewHeader interviewHeader;

    @Column(name = "validity_status")
    private String validityStatus;

    @Column(name = "event_candidate_before")
    private Long eventCandidateBefore;

    @Column(name = "event_candidate_after")
    private Long eventCandidateAfter;

    @Column(name = "interview_score")
    private Integer interviewScore;

    @Column(name = "mobile_unique_code")
    private String mobileUniqueCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "interviewDetail", orphanRemoval = true)
    private Set<InterviewImage> interviewImages;

}
