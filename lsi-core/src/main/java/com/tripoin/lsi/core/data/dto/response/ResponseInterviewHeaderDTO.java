package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewHeaderDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -5736719128595226894L;

    private Long userId;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
}
