package com.wissensalt.acl.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseData;
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
public class MenuResponseDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 939822019466891202L;

    private List<ContentMenuResponseDTO> menu;
    private ResponseData responseData;
}
