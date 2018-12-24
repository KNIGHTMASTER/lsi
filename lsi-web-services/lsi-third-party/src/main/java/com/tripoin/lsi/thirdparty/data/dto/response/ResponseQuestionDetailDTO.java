package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseIdNameDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseQuestionDetailDTO implements Serializable{
    /**
     *
     *
     */
    private static final long serialVersionUID = -8083610553107841468L;

    private Long questionId;
    private String questionName;
    private String questionType;
    private List<ResponseIdNameDTO> questionAnswers;
}
