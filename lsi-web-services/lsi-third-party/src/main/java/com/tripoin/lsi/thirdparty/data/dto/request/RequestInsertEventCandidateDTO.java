package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInsertEventCandidateDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 1957592725302000596L;
    private String eventName;
    private String eventStartTime;
    private String eventEndTime;
    private Integer isVote;
    private List<ContentInsertCandidateDTO> candidates;
}
