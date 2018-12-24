package com.wissensalt.security.oauth2.endpoint;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 7/31/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
@RequestMapping("/secured")
public class SecureEndPoint {

    @RequestLogger(name = "oauth2|secured-hello")
    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Secure Hello!";
    }

}