package com.wissensalt.api.logger.message.receiver;

import com.wissensalt.api.logger.exception.APILoggerServiceException;
import com.wissensalt.api.logger.mapper.JwtLogMapper;
import com.wissensalt.api.logger.service.ILogService;
import com.wissensalt.shared.dto.request.jwtlog.RequestJwtLogDTO;
import com.wissensalt.util.common.messaging.ISubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created on 3/1/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class FanoutJwtLogListener implements ISubscriber<RequestJwtLogDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutJwtLogListener.class);

    @Autowired
    @Qualifier(value = "jwtLogService")
    private ILogService logService;

    @Autowired
    private JwtLogMapper jwtLogMapper;

    @RabbitListener(queues = "${rabbitmq.queue.jwt-logger}")
    @Override
    public void receive(RequestJwtLogDTO p_DATA) {
        try {
            logService.insert(jwtLogMapper.convert(p_DATA));
        } catch (APILoggerServiceException e) {
            LOGGER.error("Error insert JWT Log : {}", e.toString());
        }
    }
}
