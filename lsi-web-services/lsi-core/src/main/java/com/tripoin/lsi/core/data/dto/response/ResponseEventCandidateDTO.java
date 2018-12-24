package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseEventCandidateDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8532154210704847585L;

    private ResponseInterviewEventDTO interviewEvent;
    private String candidateImagePath;
    private Integer candidateOrderNumber;
}
