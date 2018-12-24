package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.scaffolding.dto.request.RequestFunctionAssignmentDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.core.IFunctionAssignmentEndPoint;
import com.wissensalt.scaffolding.service.core.IFunctionAssignmentService;
import com.wissensalt.shared.dto.response.security.ResponseFunctionAssignmentDTO;
import com.wissensalt.shared.entity.mapper.security.FunctionAssignmentMapper;
import com.wissensalt.shared.entity.security.SecurityFunctionAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class FunctionAssignmentEndPointImpl extends AScaffoldingEndPoint<SecurityFunctionAssignment, RequestFunctionAssignmentDTO, ResponseFunctionAssignmentDTO> implements IFunctionAssignmentEndPoint {

    @Autowired
    private IFunctionAssignmentService functionAssignmentService;

    @Autowired
    private FunctionAssignmentMapper functionAssignmentMapper;
    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = functionAssignmentService;
        dataMapperIntegration = functionAssignmentMapper;
    }
}
