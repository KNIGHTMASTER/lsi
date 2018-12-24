package com.wissensalt.api.logger.service.impl;

import com.wissensalt.api.logger.dao.IJwtLogDAO;
import com.wissensalt.api.logger.entity.jwt.JwtLog;
import com.wissensalt.api.logger.exception.APILoggerServiceException;
import com.wissensalt.api.logger.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service(value = "jwtLogService")
public class JwtLogServiceImpl implements ILogService<JwtLog> {

    @Autowired
    private IJwtLogDAO jwtLogDAO;

    @Override
    public void insert(JwtLog p_DATA) throws APILoggerServiceException {
        jwtLogDAO.save(p_DATA);
    }
}
