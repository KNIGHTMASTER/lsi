package com.wissensalt.api.logger.service;

import com.wissensalt.api.logger.exception.APILoggerServiceException;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface ILogService<DATA> {

    void insert(DATA p_DATA) throws APILoggerServiceException;
}
