package com.wissensalt.api.logger.message.receiver;

import com.wissensalt.api.logger.exception.APILoggerServiceException;
import com.wissensalt.api.logger.mapper.AuthenticationLogMapper;
import com.wissensalt.api.logger.service.ILogService;
import com.wissensalt.shared.dto.request.authevent.RequestAuthenticationLogDTO;
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
public class FanoutAuthLogListener implements ISubscriber<RequestAuthenticationLogDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutAuthLogListener.class);

    @Autowired
    @Qualifier("authenticationLogService")
    private ILogService logService;

    @Autowired
    private AuthenticationLogMapper authenticationLogMapper;

    @RabbitListener(queues = "${rabbitmq.queue.auth-logger}")
    @Override
    public void receive(RequestAuthenticationLogDTO p_DATA) {
        try {
            logService.insert(authenticationLogMapper.convert(p_DATA));
        } catch (APILoggerServiceException e) {
            LOGGER.error("Error Insert Authentication Log : {}", e.toString());
        }
    }
}
