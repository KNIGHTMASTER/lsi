package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestStatementImageDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseStatementImageDTO;
import com.tripoin.lsi.core.data.model.StatementImage;
import com.tripoin.lsi.core.endpoint.IStatementImageEndPoint;
import com.tripoin.lsi.core.service.IStatementImageService;
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
public class StatementImageEndPointImpl extends AScaffoldingEndPoint<StatementImage, RequestStatementImageDTO, ResponseStatementImageDTO> implements IStatementImageEndPoint {

    @Autowired
    private IStatementImageService statementImageService;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = statementImageService;
    }
}
