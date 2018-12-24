package com.tripoin.lsi.core.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestFilterInterviewHeader implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 1608373894284363744L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private Long interviewEventId;
    private Long branchId;

}
