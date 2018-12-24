package com.wissensalt.acl.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 8/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentMenuResponseDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 939822019466891202L;

    private String code;
    private String name;
    private String link;
    private String style;
    private int order;
    private Long parent;
    private Integer status;
    private boolean isAssigned;
    private String action;
    private List<ContentMenuResponseDTO> subMenu;
}
