package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseBranchDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseUserAgentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 231620944856373374L;

    private ResponseBranchDTO branch;
    private ResponseInterviewEventDTO event;
    private Long userId;
    private String userName;
    private String name;
    private String phoneNumber;
    private String email;
    private ResponseVillageDTO village;
    private String status;
}
