package com.tripoin.lsi.core.data.dto.response.mobile;

import com.wissensalt.shared.dto.response.base.ResponseData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/22/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseMobilePointFilteringDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -6869393358773264971L;
    private Integer totalAssignedInterview;
    private Integer totalCompletedInterview;
    private Integer totalPoint;
    private ResponseData responseData;
}
