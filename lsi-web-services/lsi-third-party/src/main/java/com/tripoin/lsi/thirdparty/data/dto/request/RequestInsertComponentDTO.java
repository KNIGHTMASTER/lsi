package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInsertComponentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 703020761312555440L;
    private Long eventId;
    private String componentType;
    private List<ContentInsertComponentDTO> component;
}
