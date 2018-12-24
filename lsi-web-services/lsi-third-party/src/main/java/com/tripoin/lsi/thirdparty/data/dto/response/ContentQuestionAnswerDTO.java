package com.tripoin.lsi.thirdparty.data.dto.response;

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
public class ContentQuestionAnswerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 4794707004156611726L;
    private ResponseQuestionDTO question;
    private List<ResponseQuestionAnswerDTO> answers;
    private ResponseInterviewUserAnswerDTO userAnswer;
}
