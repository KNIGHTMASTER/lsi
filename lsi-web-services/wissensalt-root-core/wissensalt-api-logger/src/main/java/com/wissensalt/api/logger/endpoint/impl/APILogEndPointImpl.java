package com.wissensalt.api.logger.endpoint.impl;

import com.wissensalt.api.logger.endpoint.ILogEndPoint;
import com.wissensalt.api.logger.exception.APILoggerEndPointException;
import com.wissensalt.shared.dto.request.apilog.RequestAPILogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
@RequestMapping("/api/apiLog")
public class APILogEndPointImpl implements ILogEndPoint<RequestAPILogDTO> {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.fanout.exchange.api-logger}")
    private String exchangeName;

    private static final Logger LOGGER = LoggerFactory.getLogger(APILogEndPointImpl.class);

    @Override
    public void insert(@RequestBody RequestAPILogDTO p_DATA) throws APILoggerEndPointException {
        this.amqpTemplate.convertAndSend(exchangeName,"", p_DATA);
    }
}
