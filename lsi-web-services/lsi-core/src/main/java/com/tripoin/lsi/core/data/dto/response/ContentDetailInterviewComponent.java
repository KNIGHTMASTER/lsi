package com.tripoin.lsi.core.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentDetailInterviewComponent implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2817226790667968957L;

    private ResponseInterviewComponentDTO interviewComponent;
    private List<ContentQuestionAnswerDTO> questionAnswers;
}
