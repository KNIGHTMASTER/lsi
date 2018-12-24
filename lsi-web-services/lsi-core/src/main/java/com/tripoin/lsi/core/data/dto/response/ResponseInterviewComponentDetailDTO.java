package com.tripoin.lsi.core.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewComponentDetailDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -4205718646290077512L;

    private Integer componentOrder;
    private String componentType;
    private ResponseQuestionDetailDTO question;
    private String statement;
}
