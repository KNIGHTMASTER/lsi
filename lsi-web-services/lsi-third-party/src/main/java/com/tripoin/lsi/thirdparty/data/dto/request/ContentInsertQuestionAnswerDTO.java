package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertQuestionAnswerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -253355550037611694L;
    private Long questionAnswerId;
    private String name;
}
