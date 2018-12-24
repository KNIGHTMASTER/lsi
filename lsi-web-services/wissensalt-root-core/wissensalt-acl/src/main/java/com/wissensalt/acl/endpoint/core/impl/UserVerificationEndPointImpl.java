package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.scaffolding.service.core.IUserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class UserVerificationEndPointImpl {

    @Autowired
    private IUserVerificationService userVerificationService;

}
