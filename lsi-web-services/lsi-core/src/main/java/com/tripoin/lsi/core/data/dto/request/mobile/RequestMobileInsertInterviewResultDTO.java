package com.tripoin.lsi.core.data.dto.request.mobile;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/22/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestMobileInsertInterviewResultDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -1090764338670598322L;
    private String respondentName;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private String latitude;
    private String longitude;
    private String startTimeInterview;
    private String endTimeInterview;
    private Long eventCandidateBefore;
    private Long eventCandidateAfter;

}
