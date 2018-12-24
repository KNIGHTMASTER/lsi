package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.scaffolding.dto.request.RequestFunctionDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.core.IFunctionEndPoint;
import com.wissensalt.scaffolding.service.core.IFunctionService;
import com.wissensalt.shared.dto.response.security.ResponseFunctionDTO;
import com.wissensalt.shared.entity.mapper.security.FunctionMapper;
import com.wissensalt.shared.entity.security.SecurityFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class FunctionEndPointImpl extends AScaffoldingEndPoint<SecurityFunction, RequestFunctionDTO, ResponseFunctionDTO> implements IFunctionEndPoint {

    @Autowired
    private IFunctionService functionService;


    @Autowired
    private FunctionMapper functionMapper;
    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = functionService;
        dataMapperIntegration = functionMapper;
    }
}
