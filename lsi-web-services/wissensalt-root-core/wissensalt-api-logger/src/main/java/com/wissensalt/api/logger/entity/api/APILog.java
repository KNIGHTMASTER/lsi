package com.wissensalt.api.logger.entity.api;

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
@Document(collection = "api_log")
public class APILog implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -2029528692465582941L;

    @Id
    private String id;
    private Date timeStamp;
    private String serviceName;
    private String cacheControl;
    private String connection;
    private APIRequestData requestData;
    private APIResponseData responseData;
}
