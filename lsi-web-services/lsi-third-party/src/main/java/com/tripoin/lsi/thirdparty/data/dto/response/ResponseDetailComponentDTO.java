package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseDetailComponentDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -2303056497520222767L;
    private Integer componentOrder;
    private String componentType;
    private BaseResponseDTO statement;
    private ContentQuestionDTO question;
}
