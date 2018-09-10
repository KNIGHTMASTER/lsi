package com.tripoin.lsi.core.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "mst_interview_detail")
public class InterviewDetail extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5807358504761502273L;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "interview_status")
    private String interviewStatus;

    @Column(name = "respondent_name")
    private String respondentName;

    @Column(name = "respondent_age")
    private Integer respondentAge;

    @Column(name = "respondent_religion")
    private String respondentReligion;

    @Column(name = "interview_timestamp")
    private Date interviewTimeStamp;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_header_id")
    private InterviewHeader interviewHeader;
}
