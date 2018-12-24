package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertQuestionDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3505022821511840L;
    private Long questionId;
    private String name;
    private List<ContentInsertQuestionAnswerDTO> questionAnswers;
}
