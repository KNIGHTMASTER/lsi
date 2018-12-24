package com.wissensalt.api.logger.entity.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Document(collection = "auth_log")
public class AuthenticationLog implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3654918100576086192L;

    @Id
    private String id;
    private Date timeStamp;
    private String principal;
    private String type;
    private AuthenticationData data;

}
