package com.wissensalt.api.logger.entity.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 8/2/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Document(collection = "jwt_log")
public class JwtLog implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -4795755131584254111L;

    @Id
    private String id;
    private String sub;
    private String audience;
    private Date createdDate;
    private Integer expirationTime;
}
