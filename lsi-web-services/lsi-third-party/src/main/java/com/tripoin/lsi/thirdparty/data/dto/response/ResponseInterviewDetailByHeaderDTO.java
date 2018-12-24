package com.tripoin.lsi.thirdparty.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created on 9/28/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewDetailByHeaderDTO  {
    /**
     *
     *
     */
    private static final long serialVersionUID = -5503852684955327458L;
    private Long id;
    private String code;
    private String name;
    private String remarks;
    private Integer status;
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
    private String eventCandidateBefore;
    private String eventCandidateAfter;
    private Integer interviewScore;
    private List<String> interviewImages;
    private List<ContentUserAnswerBasicDTO> questionAnswers;
}
