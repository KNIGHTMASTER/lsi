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
public class ContentAssignmentAgentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8913591192148550989L;

    private Long interviewHeaderId;
    private Long provinceId;
    private String provinceName;
    private Long cityId;
    private String cityName;
    private Long districtId;
    private String districtName;
    private Long villageId;
    private String villageName;
    private String latitude;
    private String longitude;
    private Integer numberOfRespondent;
    private Integer radiusTolerance;
}
