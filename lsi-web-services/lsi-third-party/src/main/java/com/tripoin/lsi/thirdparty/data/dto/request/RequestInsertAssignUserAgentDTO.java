package com.tripoin.lsi.thirdparty.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * Created on 9/18/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestInsertAssignUserAgentDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 4101041370691572610L;

    private String name;
    private String userName;
    private String password;
    private String  expiredDate;
    private String phoneNumber;
    private String email;
    private Long eventId;
    private Long branchId;
    private Map<Long, Integer> mapVillageRespondent;
}
