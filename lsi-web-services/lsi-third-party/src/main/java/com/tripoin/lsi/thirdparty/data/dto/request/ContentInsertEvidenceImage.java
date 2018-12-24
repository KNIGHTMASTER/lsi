package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 10/4/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentInsertEvidenceImage implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7724893930136387338L;

    private String filePath;
    private Long fileSize;
    private String fileType;
    private String latitude;
    private String longitude;
}
