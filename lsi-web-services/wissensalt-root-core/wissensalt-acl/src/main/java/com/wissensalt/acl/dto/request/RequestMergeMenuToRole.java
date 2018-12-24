package com.wissensalt.acl.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestMergeMenuToRole implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2530345187704745253L;
    private Map<Long, ContentMergeMenuToRole> mapMenuEnable;
    private Long roleId;
}
