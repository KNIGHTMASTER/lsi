package com.tripoin.lsi.core.data.dto.response.mobile;

import com.tripoin.lsi.core.data.dto.response.ContentAssignmentAgentDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseMobileFindDetailAssignmentByAgentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3265867499877156768L;
    private String name;
    private List<ContentAssignmentAgentDTO> agentAssignments;
    private Integer totalAssignedInterview;
    private Integer totalCompletedInterview;
    private Integer totalPoint;
    private ResponseData responseData;
}
