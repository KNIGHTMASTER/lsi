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
public class ResponseQuestionDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7874849757036883636L;

    private ResponseInterviewComponentDTO interviewComponent;
    private String questionType;
}
