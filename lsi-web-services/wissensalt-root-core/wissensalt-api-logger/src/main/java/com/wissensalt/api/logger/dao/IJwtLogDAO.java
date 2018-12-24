package com.wissensalt.api.logger.dao;

import com.wissensalt.api.logger.entity.jwt.JwtLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created on 8/2/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IJwtLogDAO extends MongoRepository<JwtLog, String>{
}
