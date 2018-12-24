package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentQuestionDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5851892075158024137L;
    private String questionType;
    private List<BaseResponseDTO> questionAnswers;
}
