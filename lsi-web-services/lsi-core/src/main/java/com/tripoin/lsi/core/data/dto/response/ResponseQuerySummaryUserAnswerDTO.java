package com.tripoin.lsi.core.data.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Created on 10/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseQuerySummaryUserAnswerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -5970507332194232282L;
    private Long villageId;
    private String question;
    private String answer;
}
