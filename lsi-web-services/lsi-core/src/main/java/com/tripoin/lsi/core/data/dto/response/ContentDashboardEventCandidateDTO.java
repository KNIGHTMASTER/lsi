package com.tripoin.lsi.core.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentDashboardEventCandidateDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2723659577926090295L;
    private Long id;
    private String name;
    private Integer number;
}
