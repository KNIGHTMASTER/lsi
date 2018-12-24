package com.wissensalt.security.oauth2.endpoint;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created on 8/2/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
@RequestMapping("/principal")
public class PrincipalEndPoint {

    @RequestLogger(name = "oauth2|principal-me")
    @GetMapping("/me")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
}
