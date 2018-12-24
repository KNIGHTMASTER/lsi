package com.wissensalt.acl.endpoint.impl;

import com.wissensalt.acl.endpoint.IIdentityTypeEndPoint;
import com.wissensalt.scaffolding.dto.request.RequestIdentityTypeDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.IIdentityTypeService;
import com.wissensalt.shared.dto.response.master.ResponseIdentityTypeDTO;
import com.wissensalt.shared.entity.mapper.master.IdentityTypeMapper;
import com.wissensalt.shared.entity.master.IdentityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 8/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class IdentityTypeEndPointImpl extends AScaffoldingEndPoint<IdentityType, RequestIdentityTypeDTO, ResponseIdentityTypeDTO> implements IIdentityTypeEndPoint {

    @Autowired
    private IIdentityTypeService identityTypeService;

    @Autowired
    private IdentityTypeMapper identityTypeMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = identityTypeService;
        dataMapperIntegration = identityTypeMapper;
    }
}
