package com.tripoin.lsi.core.data.dto.response;

import com.wissensalt.shared.dto.response.security.ResponseBranchDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 9/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseDetailUserAgentById implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -1967181142952309633L;

    private ResponseInterviewEventDTO event;
    private ResponseBranchDTO branch;
    private String userName;
    private String name;
    private String phoneNumber;
    private String email;
    private List<ContentDetailUserAgent> detailAssignment;
}
