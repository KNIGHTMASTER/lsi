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
public class ContentInsertCandidateDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -770802066087713068L;

    private String name;
    private String candidateImagePath;
    private Integer candidateOrderNumber;
}
