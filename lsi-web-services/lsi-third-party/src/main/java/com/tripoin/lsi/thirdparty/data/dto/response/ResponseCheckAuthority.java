package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
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
public class ResponseCheckAuthority implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5335722137377653541L;
    private ResponseUserDTO user;
    private ResponseRoleDTO role;
    private ResponseData responseData;
}
