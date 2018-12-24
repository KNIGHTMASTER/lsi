package com.wissensalt.security.oauth2.endpoint;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 7/31/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
@RequestMapping("/echo")
public class EchoEndPoint {

    @RequestLogger(name = "oauth2|hello")
    @GetMapping("/{param}")
    public ResponseEntity echo(@PathVariable("param") String p_Param, HttpServletRequest p_HttpServletRequest) {
        String response = "Hello ";
        if (p_Param != null) {
            response = response.concat(p_Param);
        }
        return ResponseEntity.ok(response);
    }
}
