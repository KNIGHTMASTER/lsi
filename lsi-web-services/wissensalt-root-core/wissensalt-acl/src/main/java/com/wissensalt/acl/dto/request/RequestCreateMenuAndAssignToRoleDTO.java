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
public class RequestCreateMenuAndAssignToRoleDTO implements Serializable{
    /**
     *
     *
     */
    private static final long serialVersionUID = -626908428459313450L;
    private Long roleId;
    private List<RequestCreateMenuDTO> menuList;
}
