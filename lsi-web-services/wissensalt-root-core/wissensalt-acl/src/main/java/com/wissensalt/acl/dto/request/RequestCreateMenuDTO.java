package com.wissensalt.acl.dto.request;

import com.wissensalt.shared.dto.request.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 8/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestCreateMenuDTO extends BaseRequestDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -783299038609016309L;
    private String url;
    private String style;
    private Integer order;
    private Long parentMenu;
}
