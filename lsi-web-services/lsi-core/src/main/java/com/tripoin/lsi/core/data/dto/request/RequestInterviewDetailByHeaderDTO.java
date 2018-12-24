package com.tripoin.lsi.core.data.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 10/8/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewDetailByHeaderDTO extends PaginationDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 4685291440186567841L;
    private Long id;
}
