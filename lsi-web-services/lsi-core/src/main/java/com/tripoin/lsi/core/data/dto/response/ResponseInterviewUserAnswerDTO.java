package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewUserAnswerDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3427150135411663468L;

    private ResponseQuestionDTO question;
    private ResponseQuestionAnswerDTO answer;
    private String otherAnswer;
}
