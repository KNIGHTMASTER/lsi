package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/23/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestFindUserAgentDTO extends PaginationDTO{
    /**
     *
     *
     */
    private static final long serialVersionUID = 3607283877958203543L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
    private Long eventId;
    private String name;
    private String userName;
}
