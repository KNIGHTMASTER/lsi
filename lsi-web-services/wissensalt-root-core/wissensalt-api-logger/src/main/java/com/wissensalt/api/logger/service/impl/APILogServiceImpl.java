package com.wissensalt.api.logger.service.impl;

import com.wissensalt.api.logger.dao.IAPILogDAO;
import com.wissensalt.api.logger.exception.APILoggerServiceException;
import com.wissensalt.api.logger.service.ILogService;
import com.wissensalt.api.logger.entity.api.APILog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service(value = "apiLogService")
public class APILogServiceImpl implements ILogService<APILog> {

    @Autowired
    private IAPILogDAO apiLogDAO;

    @Override
    public void insert(APILog p_DATA) throws APILoggerServiceException {
        apiLogDAO.save(p_DATA);
    }
}
