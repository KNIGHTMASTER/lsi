package com.tripoin.lsi.core.data.dto.request;

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
public class ContentInsertStatementDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 3307951713504943906L;
    private Long statementId;
    private String name;
}
