package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestParameterGroupDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseParameterGroupDTO;
import com.tripoin.lsi.core.data.mapper.ParameterGroupMapper;
import com.tripoin.lsi.core.data.model.ParameterGroup;
import com.tripoin.lsi.core.endpoint.IParameterGroupEndPoint;
import com.tripoin.lsi.core.service.IParameterGroupService;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class ParameterGroupEndPointImpl extends AScaffoldingEndPoint<ParameterGroup, RequestParameterGroupDTO, ResponseParameterGroupDTO> implements IParameterGroupEndPoint {

    @Autowired
    private IParameterGroupService parameterGroupService;

    @Autowired
    private ParameterGroupMapper parameterGroupMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = parameterGroupService;
        dataMapperIntegration = parameterGroupMapper;
    }
}
