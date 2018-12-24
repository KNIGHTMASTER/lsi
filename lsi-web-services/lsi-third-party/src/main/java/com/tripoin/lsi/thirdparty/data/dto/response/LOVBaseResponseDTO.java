package com.tripoin.lsi.thirdparty.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 11/29/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class LOVBaseResponseDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 115803980523662254L;
    private Long id;
    private String name;
}
