package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewDashboardDTO extends RequestFilterInterviewHeader {
    /**
     *
     *
     */
    private static final long serialVersionUID = 6965279473405823200L;
    private Long volunteerId;
}
