package com.wissensalt.acl.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 10/4/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentMergeMenuToRole implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -2085234518271206112L;
    private Boolean enabled;
    private String action;
}
