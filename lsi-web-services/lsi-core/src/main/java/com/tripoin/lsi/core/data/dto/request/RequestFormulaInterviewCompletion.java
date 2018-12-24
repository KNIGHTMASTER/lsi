package com.tripoin.lsi.core.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestFormulaInterviewCompletion implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8519209307274130311L;
    private Integer numberOfRespondent;
    private Integer numberOfCompletedInterview;
}
