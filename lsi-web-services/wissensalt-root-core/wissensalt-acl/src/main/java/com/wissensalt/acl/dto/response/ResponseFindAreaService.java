package com.wissensalt.acl.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseFindAreaService implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -155009044395351826L;
    private Integer villageSize;
    List<ResponseAreaDTO> content;
}
