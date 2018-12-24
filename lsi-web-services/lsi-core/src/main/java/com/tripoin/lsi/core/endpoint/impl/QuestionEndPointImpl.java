package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestQuestionDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseQuestionDTO;
import com.tripoin.lsi.core.data.mapper.QuestionMapper;
import com.tripoin.lsi.core.data.model.Question;
import com.tripoin.lsi.core.endpoint.IQuestionEndPoint;
import com.tripoin.lsi.core.service.IQuestionService;
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
public class QuestionEndPointImpl extends AScaffoldingEndPoint<Question, RequestQuestionDTO, ResponseQuestionDTO> implements IQuestionEndPoint {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = questionService;
        dataMapperIntegration = questionMapper;
    }
}
