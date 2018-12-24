package com.wissensalt.security.oauth2.config;

import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.core.IAuthenticationService;
import com.wissensalt.shared.entity.security.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created on 8/1/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class OAuthClientDetailsService implements UserDetailsService{

    @Autowired
    private IAuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthClientDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecurityUser user = null;
        try {
            user = authenticationService.login(s);
        } catch (ServiceException e) {
            LOGGER.error("Error Login {}", e.toString());
        }
        if (user != null) {
            if (user.getPassword() == null || user.getPassword().trim().length() <= 0) {
                user.setPassword("");
            }
            return user;
        }else {
            LOGGER.error("User is not found");
            throw new UsernameNotFoundException("User is not found");
        }
    }
}
