package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentDetailUserAgent implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 6740770779138047992L;
    private Long interviewHeaderId;
    private ResponseInterviewEventDTO event;
    private ResponseVillageDTO village;
    private String status;
}
