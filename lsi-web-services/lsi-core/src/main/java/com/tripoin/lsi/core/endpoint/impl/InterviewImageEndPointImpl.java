package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewImageDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseInterviewImageDTO;
import com.tripoin.lsi.core.data.mapper.InterviewImageMapper;
import com.tripoin.lsi.core.data.model.InterviewImage;
import com.tripoin.lsi.core.endpoint.IInterviewImageEndPoint;
import com.tripoin.lsi.core.service.IInterviewImageService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewImageEndPointImpl extends AScaffoldingEndPoint<InterviewImage, RequestInterviewImageDTO, ResponseInterviewImageDTO> implements IInterviewImageEndPoint{

    @Autowired
    private IInterviewImageService interviewImageService;

    @Autowired
    private InterviewImageMapper interviewImageMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.fanout.exchange.file-upload}")
    private String exchangeName;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = interviewImageService;
        dataMapperIntegration = interviewImageMapper;
    }

    @RequestLogger(name = "Interview Image|Insert Through Messaging")
    @Override
    public ResponseData insertThroughMessaging(HttpServletRequest httpServletRequest, @RequestBody RequestInterviewImageDTO requestInterviewImageDTO) throws EndPointException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        amqpTemplate.convertAndSend(exchangeName, "", requestInterviewImageDTO);
        return responseData;
    }
}
