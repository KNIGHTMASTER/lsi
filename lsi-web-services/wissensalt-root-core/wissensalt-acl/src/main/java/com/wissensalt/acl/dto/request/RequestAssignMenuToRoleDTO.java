package com.wissensalt.acl.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 8/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestAssignMenuToRoleDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -4558982997864072445L;
    private Long roleId;
    private List<Long> menuIds;
}
