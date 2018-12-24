package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentAreaProgressDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2577701571078833631L;
    private ResponseVillageDTO village;
    private Integer numberOfCompletedInterview;
    private Integer numberOfRespondent;
    private Integer numberOfDataValid;
    private Double numberOfInterviewCompletion;
    private Double numberOfInterviewValidity;
}
