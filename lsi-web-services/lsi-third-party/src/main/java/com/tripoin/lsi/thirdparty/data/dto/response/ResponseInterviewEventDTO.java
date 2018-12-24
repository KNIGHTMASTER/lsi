package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewEventDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -6439105239773014467L;

    private String eventStartDate;
    private String eventEndDate;
    private Integer isVote;
}
