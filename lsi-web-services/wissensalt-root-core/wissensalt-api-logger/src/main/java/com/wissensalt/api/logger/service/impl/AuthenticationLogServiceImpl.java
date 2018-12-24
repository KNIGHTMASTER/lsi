package com.wissensalt.api.logger.service.impl;

import com.wissensalt.api.logger.dao.IAuthenticationLogDAO;
import com.wissensalt.api.logger.exception.APILoggerServiceException;
import com.wissensalt.api.logger.service.ILogService;
import com.wissensalt.api.logger.entity.auth.AuthenticationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service("authenticationLogService")
public class AuthenticationLogServiceImpl implements ILogService<AuthenticationLog> {

    @Autowired
    private IAuthenticationLogDAO authenticationLogDAO;


    @Override
    public void insert(AuthenticationLog p_DATA) throws APILoggerServiceException {
        authenticationLogDAO.save(p_DATA);
    }
}
