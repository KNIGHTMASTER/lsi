package com.wissensalt.acl.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/26/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestUpdatePhoneNumberPasswordDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 6942577341559330728L;
    private String userName;
    private String name;
    private String phoneNumber;
    private String oldPassword;
    private String newPassword;
}
