package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.acl.endpoint.core.IBranchEndPoint;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.dto.request.RequestBranchDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.IBranchService;
import com.wissensalt.shared.dto.response.security.ResponseBranchDTO;
import com.wissensalt.shared.entity.mapper.security.BranchMapper;
import com.wissensalt.shared.entity.security.SecurityBranch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class BranchEndPointImpl extends AScaffoldingEndPoint<SecurityBranch, RequestBranchDTO, ResponseBranchDTO> implements IBranchEndPoint {

    @Autowired
    private IBranchService branchService;

    @Autowired
    private BranchMapper branchMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchEndPointImpl.class);

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = branchService;
        dataMapperIntegration = branchMapper;
    }

    @RequestLogger(name = "Branch-Get Branch By UserName")
    @Override
    public ResponseBranchDTO getBranchByUserName(HttpServletRequest httpServletRequest, Principal p_Principal) throws EndPointException {
        try {
            return branchService.findByUserName(p_Principal.getName());
        } catch (ServiceException e) {
            LOGGER.error("Error Find Branch By User Name {}", e.toString());
            return null;
        }
    }
}
