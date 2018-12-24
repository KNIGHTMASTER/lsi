package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewDetailDTO extends BaseMasterDATA<Long> {
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
    private ResponseInterviewHeaderDTO interviewHeader;
    private String validityStatus;
    private ResponseEventCandidateDTO eventCandidateBefore;
    private ResponseEventCandidateDTO eventCandidateAfter;
    private Integer interviewScore;
    private List<String> interviewImages;
}
