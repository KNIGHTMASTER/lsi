package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseInterviewImageDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = -7496878547659845274L;

    private String uploadTimeStamp;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private Double latitude;
    private Double longitude;
    private ResponseInterviewDetailDTO interviewDetail;
}
