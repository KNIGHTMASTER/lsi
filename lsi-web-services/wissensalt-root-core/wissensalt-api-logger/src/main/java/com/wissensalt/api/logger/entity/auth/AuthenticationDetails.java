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
public class AuthenticationDetails implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5036549105945976222L;

    private String remoteAddress;
    private String sessionId;
}
