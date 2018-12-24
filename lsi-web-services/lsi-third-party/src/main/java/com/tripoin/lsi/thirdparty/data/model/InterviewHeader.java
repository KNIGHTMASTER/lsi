package com.tripoin.lsi.thirdparty.data.model;

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
@Table(name = "trx_interview_header")
public class InterviewHeader extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 8247607405995769760L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "number_of_respondent")
    private Integer numberOfRespondent;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_event_id")
    private InterviewEvent interviewEvent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "interviewHeader", orphanRemoval = true)
    private Set<InterviewDetail> interviewDetails;

}
