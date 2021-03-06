package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewProgressByStatusDTO extends PaginationDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 1223904278566637649L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private String validityStatus;
    private Long interviewEventId;
}
