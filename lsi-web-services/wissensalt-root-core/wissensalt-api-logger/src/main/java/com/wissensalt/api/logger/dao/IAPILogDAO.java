package com.wissensalt.api.logger.dao;

import com.wissensalt.api.logger.entity.api.APILog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IAPILogDAO extends MongoRepository<APILog, String> {
}
