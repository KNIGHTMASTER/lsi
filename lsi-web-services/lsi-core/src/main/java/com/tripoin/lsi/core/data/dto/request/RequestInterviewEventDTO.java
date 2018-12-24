package com.tripoin.lsi.core.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewEventDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = -407825873205019684L;

    private String eventStartDate;
    private String eventEndDate;
    private Integer isVote;
}
