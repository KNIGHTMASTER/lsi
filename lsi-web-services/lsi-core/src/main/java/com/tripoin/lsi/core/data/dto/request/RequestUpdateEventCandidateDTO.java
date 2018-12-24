package com.tripoin.lsi.core.data.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@ToString
@Getter
@Setter
public class RequestUpdateEventCandidateDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 1957592725302000596L;
    private Long id;
    private String eventName;
    private String eventStartTime;
    private String eventEndTime;
    private Integer isVote;
    private List<ContentUpdateCandidateDTO> candidates;
}
