package com.wissensalt.acl.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInsertAreaDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 7571895299380654413L;
    private Long provinceId;
    private Long cityId;
    private Long districtName;
    private Long villageName;
    private Integer numberOfFamilyLeader;
}
