package com.tripoin.lsi.core.messaging;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewImageDTO;
import com.tripoin.lsi.core.service.IInterviewImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/7/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewImageReceiver {

    @Autowired
    private IInterviewImageService interviewImageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewImageReceiver.class);

    @RabbitListener(queues = "${rabbitmq.queue.file-upload}")
    public void receive(RequestInterviewImageDTO p_RequestInterviewImageDTO) {
        try {
            interviewImageService.insertThroughMessaging(p_RequestInterviewImageDTO);
        } catch (Exception e) {
            LOGGER.error("Error Saving Interview Image {} ",e.toString());
        }
    }
}
