package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * Created on 10/21/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseSummaryUserAnswerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -8477758219206295527L;
    private ResponseVillageDTO village;
    private Map<String, Map<String, Integer>> mapContent;
}
