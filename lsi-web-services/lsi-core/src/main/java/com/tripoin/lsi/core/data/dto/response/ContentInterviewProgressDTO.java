package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInterviewProgressDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -1081373488182424369L;
    private Long interviewDetailId;
    private ResponseVillageDTO village;
    private String validityStatus;
    private String respondentName;
    private String respondentPhoneNumber;
    private String respondentAddress;
    private String latitude;
    private String longitude;
    private String remarks;
    private List<ResponseBasicInterviewImageDTO> evidences;
}
