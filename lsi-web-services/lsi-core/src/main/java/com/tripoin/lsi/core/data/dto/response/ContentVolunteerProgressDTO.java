package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseCompanyDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
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
public class ContentVolunteerProgressDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2217524556475817340L;

    private Long interviewHeaderId;
    private ResponseVillageDTO village;
    private ResponseCompanyDTO company;
    private ResponseUserDTO volunteer;
    private String interviewCompletion;
    private String interviewValidity;
}
