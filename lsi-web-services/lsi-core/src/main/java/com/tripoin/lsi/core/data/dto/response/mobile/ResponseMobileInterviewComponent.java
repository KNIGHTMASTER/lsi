package com.tripoin.lsi.core.data.dto.response.mobile;

import com.tripoin.lsi.core.data.dto.response.ResponseInterviewComponentDetailDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseMobileInterviewComponent implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -1912673346591392281L;
    private String startTimeStamp;
    private String endTimeStamp;
    private List<ContentMobileEventCandidate> eventCandidatesBefore;
    private List<ResponseInterviewComponentDetailDTO> interviewComponents;
    private List<ContentMobileEventCandidate> eventCandidatesAfter;
    private ResponseData responseData;
}
