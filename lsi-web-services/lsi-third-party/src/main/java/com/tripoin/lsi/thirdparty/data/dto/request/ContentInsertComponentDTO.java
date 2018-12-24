package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertComponentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -770802066087713068L;
    private Long componentId;
    private String componentType;
    private Integer componentOrder;
    private ContentInsertQuestionDTO questions;
    private ContentInsertStatementDTO statement;
}
