package com.tripoin.lsi.core.data.dto.response.mobile;

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
public class ContentMobileEventCandidate implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -2222284286075499897L;
    private String name;
    private String imagePath;
    private Integer candidateOrder;
}
