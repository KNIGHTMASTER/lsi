package com.tripoin.lsi.core.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewHeaderDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7552019251588710953L;

    private Long userId;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private Long branchId;
    private Integer numberOfRespondent;
    private Long interviewEventId;
}
