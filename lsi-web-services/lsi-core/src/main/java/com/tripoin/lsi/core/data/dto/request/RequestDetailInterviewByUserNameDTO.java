package com.tripoin.lsi.core.data.dto.request;

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
public class RequestDetailInterviewByUserNameDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 150101749354139455L;

    private String userName;
}
