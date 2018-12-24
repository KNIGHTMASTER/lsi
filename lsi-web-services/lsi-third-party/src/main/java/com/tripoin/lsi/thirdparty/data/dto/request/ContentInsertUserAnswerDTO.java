package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/28/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertUserAnswerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 9023935382010518391L;
    private Long questionId;
    private Long answerId;
    private String otherAnswer;
}
