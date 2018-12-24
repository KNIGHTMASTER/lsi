package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestEventCandidateDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = 1558743798641869917L;
    private Long interviewEventId;
    private String candidateImagePath;
    private Integer candidateOrderNumber;
}
