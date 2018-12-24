package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 10/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseBasicInterviewImageDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 3070925417655114951L;

    private String uploadTimeStamp;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private Double latitude;
    private Double longitude;
}
