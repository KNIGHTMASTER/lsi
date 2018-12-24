package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInterviewImageDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = -3568235399517924944L;

    private String uploadTimeStamp;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private Long interviewDetailId;
    private String latitude;
    private String longitude;
}
