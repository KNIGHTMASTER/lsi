package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionAnswerDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseQuestionAnswerDTO;
import com.tripoin.lsi.core.data.mapper.QuestionAnswerMapper;
import com.tripoin.lsi.core.data.model.QuestionAnswer;
import com.tripoin.lsi.core.endpoint.IQuestionAnswerEndPoint;
import com.tripoin.lsi.core.service.IQuestionAnswerService;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class QuestionAnswerEndPointImpl extends AScaffoldingEndPoint<QuestionAnswer, RequestQuestionAnswerDTO, ResponseQuestionAnswerDTO> implements IQuestionAnswerEndPoint {
    @Autowired
    private IQuestionAnswerService questionAnswerService;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = questionAnswerService;
        dataMapperIntegration = questionAnswerMapper;
    }
}
