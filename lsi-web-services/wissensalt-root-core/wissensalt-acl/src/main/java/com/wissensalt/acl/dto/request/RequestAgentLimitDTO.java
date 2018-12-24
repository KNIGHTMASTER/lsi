package com.wissensalt.acl.dto.request;

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
public class RequestAgentLimitDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 6530148073147630715L;

    private String name;
    private String userName;
    private Integer limit;
}
