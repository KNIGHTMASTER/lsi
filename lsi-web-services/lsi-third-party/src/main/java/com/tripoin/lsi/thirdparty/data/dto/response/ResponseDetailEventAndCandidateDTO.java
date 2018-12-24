package com.tripoin.lsi.thirdparty.data.dto.response;

import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseDetailEventAndCandidateDTO implements Serializable{
    /**
     *
     *
     */
    private static final long serialVersionUID = -5091440226096447561L;
    private ResponseInterviewEventDTO event;
    private List<ResponseEventCandidateDTO> candidates;
    private ResponseData responseData;
}
