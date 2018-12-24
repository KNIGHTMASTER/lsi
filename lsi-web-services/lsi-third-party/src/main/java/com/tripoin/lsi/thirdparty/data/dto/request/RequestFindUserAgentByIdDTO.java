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
public class RequestFindUserAgentByIdDTO extends PaginationDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -7749027860517158857L;
    private Long id;
}
