package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseDetailInterviewDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5404620219699874995L;

    private String eventName;
    private String province;
    private String city;
    private String district;
    private String village;
    private String volunteerName;
    private String phoneNumber;
    private String email;
    private String respondentName;
    private String respondentPhoneNumber;
    private String respondentAddress;
    private String interviewStartTime;
    private String interviewEndTime;
    private String latitude;
    private String longitude;
    private String validityStatus;
    private String remarks;
    private List<ContentDetailInterviewComponent> detailComponents;
    private List<ResponseInterviewImageDTO> evidences;
    private ResponseData responseData;

}
