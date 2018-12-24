package com.wissensalt.acl.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseAreaDTO implements Serializable{
    /**
     *
     *
     */
    private static final long serialVersionUID = -893979866126381113L;
    private String province;
    private String city;
    private String district;
    private ResponseVillageDTO village;
}
