package com.tripoin.lsi.core.data.dto.request.mobile;

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
public class RequestMobilePointFilteringDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7088677812957395967L;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private Long villageId;
}
