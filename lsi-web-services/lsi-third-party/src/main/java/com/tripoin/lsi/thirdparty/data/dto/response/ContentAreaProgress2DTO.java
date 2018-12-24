package com.tripoin.lsi.thirdparty.data.dto.response;

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
public class ContentAreaProgress2DTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2577701571078833631L;
    private String province;
    private String city;
    private String district;
    private String village;
    private String volunteer;
    private Integer completedInterview;
    private Integer validInterview;
    private Integer respondent;
}
