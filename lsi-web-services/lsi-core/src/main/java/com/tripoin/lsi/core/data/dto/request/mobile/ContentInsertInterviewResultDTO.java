package com.tripoin.lsi.core.data.dto.request.mobile;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/22/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertInterviewResultDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3317594861538223605L;

    private Long questionId;
    private Long answerId;
    private String otherAnswer;
}
