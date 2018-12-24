package com.tripoin.lsi.thirdparty.data.dto.request;

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
public class RequestAgentAssignmentDTO implements Serializable{
    /**
     *
     *
     */
    private static final long serialVersionUID = 1884093458723731645L;
    private Long eventId;
    private Long agentId;
    private List<ContentAgentAssignmentDTO> contentAgentAssignments;
}
