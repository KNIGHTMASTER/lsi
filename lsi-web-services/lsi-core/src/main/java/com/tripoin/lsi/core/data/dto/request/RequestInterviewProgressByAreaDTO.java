package com.tripoin.lsi.core.data.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewProgressByAreaDTO extends PaginationDTO{
    /**
     *
     *
     */
    private static final long serialVersionUID = 7111794803502308484L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private Long interviewEventId;
}
