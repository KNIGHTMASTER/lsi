package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseParameterDTO extends BaseResponseDTO {
    /**
     *
     *
     */
    private static final long serialVersionUID = 5820416459592010028L;
    private ResponseParameterGroupDTO parameterGroup;
}