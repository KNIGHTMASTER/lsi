package com.tripoin.lsi.core.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentUserAnswerBasicDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 4794707004156611726L;
    private String question;
    private String Answer;
}
