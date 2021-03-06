package com.tripoin.lsi.core.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewDetailDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8159599180535975021L;

    private String latitude;
    private String longitude;
    private String interviewStatus;
    private String respondentName;
    private Integer respondentAge;
    private String respondentReligion;
    private String respondentAddress;
    private String respondentPhoneNumber;
    private String interviewStartDate;
    private String interviewEndDate;
    private Long interviewHeaderId;
    private String validityStatus;
    private Long eventCandidateBefore;
    private Long eventCandidateAfter;
    private Integer interviewScore;
}
