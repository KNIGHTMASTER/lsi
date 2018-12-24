package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseDashboardInterview implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -7196335440147480141L;
    private Integer numberOfCompleteData;
    private Integer numberOfNotCompleteData;
    private Integer numberOfValidData;
    private Integer numberOfNotValidData;
    private Integer numberOfValidEnoughData;
    private Integer numberOfLessValidData;
    private List<ContentDashboardEventCandidateDTO> eventCandidateBefore;
    private List<ContentDashboardEventCandidateDTO> eventCandidateAfter;
    Map<String, Map<String, Integer>> userAnswers;
    private ResponseData responseData;
}
