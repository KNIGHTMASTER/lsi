package com.wissensalt.security.oauth2.config;

import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.core.IAuthenticationService;
import com.wissensalt.shared.entity.security.SecurityUser;
import com.wissensalt.util.common.network.CommonNetworkUtils;
import com.wissensalt.util.common.time.TimeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created on 5/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Component
public class OAuth2AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Value("${oauth.token-validity-in-seconds}")
    private Integer tokenValidity;

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthenticationProvider.class);

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityUser user = null;
        String ipAddress = request.getRemoteAddr();
        String headerIpAddress = request.getHeader("ipaddress");
        String hostIpLocal = CommonNetworkUtils.getHostIp();

        String clientIdentifier;
        if (headerIpAddress != null && headerIpAddress.length() > 0) {
            clientIdentifier = headerIpAddress;
        }else {
            clientIdentifier = ipAddress+"|"+hostIpLocal;
        }

        LOGGER.info("Client Identifier {}", clientIdentifier);
        try {
            user = authenticationService.login(authentication.getName());
        } catch (ServiceException e) {
            LOGGER.error("Error Login {}", e.toString());
        }
        if (user != null) {

            if (user.getPassword() == null || user.getPassword().trim().length() <= 0) {
                user.setPassword("");
            }

            boolean canLogin = false;
            if (passwordEncoder.matches(String.valueOf(authentication.getCredentials()), user.getPassword())) {
                LOGGER.info("Updating Login Status {}", clientIdentifier);
                try {
                    authenticationService.updateStatusLoggedIn(user, clientIdentifier);
                } catch (ServiceException e) {
                    LOGGER.error("Error Update Login Status {}", e.toString());
                }
                return super.authenticate(authentication);
                /*if (user.getIpAddress() != null) {
                    if (user.getIpAddress().equals(clientIdentifier)) {
                        LOGGER.info("Login Using Same Prev Ip Identifier");
                        canLogin = true;
                    }else {
                        canLogin = capableToLogin(user);
                    }
                }else {
                    LOGGER.error("Anonymous User Tried to Login With Unknown IP Address");
                    canLogin = capableToLogin(user);
                }

                if (canLogin) {
                    LOGGER.info("Updating Login Status {}", clientIdentifier);
                    try {
                        authenticationService.updateStatusLoggedIn(user, clientIdentifier);
                    } catch (ServiceException e) {
                        LOGGER.error("Error Update Login Status {}", e.toString());
                    }
                    return super.authenticate(authentication);
                }else {
                    LOGGER.error("Can Not Logged In - Update Login Status");
                    throw new UserDeniedAuthorizationException("Access Denied");
                }*/
            }else {
                LOGGER.warn("Password User {} does not match", authentication.getName());
                throw new BadClientCredentialsException();
            }
        }else {
            LOGGER.error("User {} is not found", authentication.getName());
            throw new UnauthorizedUserException("User Is Not Found");
        }
    }

    private boolean capableToLogin(SecurityUser user) {
        Boolean canLogin = false;
        /*Token Is Not Expired Yet*/
        if (user.getLastLogin() != null) {
            Integer nMinutesExpired = tokenValidity/60;
            if (TimeCalculator.addMinutesToDate(nMinutesExpired, user.getLastLogin()).compareTo(new Date()) >= 0) {
                LOGGER.info("Prev Token Is Not Expired Yet, Try Checking Login Status {}", user.getLoginStatus());
                if (user.getLoginStatus() == Boolean.TRUE) {
                    LOGGER.error("Can Not Login Using Multiple Device At Same Time");
                    throw new UserDeniedAuthorizationException("Can Not Login Using Multiple Device At Same Time");
                }else {
                    canLogin = true;
                }
            }else {
                LOGGER.info("Prev Token Has Been Expired");
                            /*Token Is Has Been Expired*/
                canLogin = true;
            }
        }else {
            canLogin = true;
        }
        return canLogin;
    }
}
