package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 10/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestSummaryUserAnswerDTO extends PaginationDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3090197209820883774L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private Long interviewEventId;
    private Long branchId;
}
