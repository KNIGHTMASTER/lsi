package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentAgentAssignmentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7758060026624922366L;

    private Long interviewHeaderId;
    private Long villageId;
    private Integer numberOfRespondent;
}
