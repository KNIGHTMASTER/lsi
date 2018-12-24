package com.wissensalt.api.logger.entity.auth;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class AuthenticationData implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 8479284630831420649L;
    private String requestUrl;
    private String type;
    private String message;
    private AuthenticationDetails details;
}
