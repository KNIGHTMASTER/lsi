package com.tripoin.lsi.thirdparty.data.dto.request;

import com.wissensalt.shared.entity.BaseMasterDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestParameterDTO extends BaseMasterDATA<Long> {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5119735667147387532L;
    private Long parameterGroupId;
}
