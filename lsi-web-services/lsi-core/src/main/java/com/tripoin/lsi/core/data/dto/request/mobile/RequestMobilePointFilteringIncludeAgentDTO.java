package com.tripoin.lsi.core.data.dto.request.mobile;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/22/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestMobilePointFilteringIncludeAgentDTO extends RequestMobilePointFilteringDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7088677812957395967L;
    private Long agentId;
}
