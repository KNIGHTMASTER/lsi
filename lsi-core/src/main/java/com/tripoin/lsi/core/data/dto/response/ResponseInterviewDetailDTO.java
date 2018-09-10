package com.tripoin.lsi.core.data.dto.response;

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
public class ResponseInterviewDetailDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8159599180535975021L;

    private Double latitude;
    private Double longitude;
    private String interviewStatus;
    private String respondentName;
    private Integer respondentAge;
    private String respondentReligion;
    private String interviewTimeStamp;
    private ResponseInterviewHeaderDTO interviewHeader;
}
