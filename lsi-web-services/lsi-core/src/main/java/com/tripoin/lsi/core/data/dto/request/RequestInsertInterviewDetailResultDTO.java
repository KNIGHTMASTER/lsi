package com.tripoin.lsi.core.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/28/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInsertInterviewDetailResultDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 873177890230104878L;
    private Long interviewHeaderId;
    private Integer eventCandidateBefore;
    private Integer eventCandidateAfter;
    private Integer interviewScore;
    private String respondentName;
    private String respondentPhoneNumber;
    private String mobileUniqueCode;
    private String respondentAddress;
    private String latitude;
    private String longitude;
    private String interviewStartTime;
    private String interviewEndTime;
    private List<ContentInsertEvidenceImage> evidenceImagePath;
    private List<ContentInsertUserAnswerDTO> userAnswers;
    private String remarks;
}
