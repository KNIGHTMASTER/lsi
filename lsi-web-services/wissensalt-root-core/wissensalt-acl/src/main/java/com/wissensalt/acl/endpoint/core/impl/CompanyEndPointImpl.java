package com.wissensalt.acl.endpoint.core.impl;

import com.wissensalt.scaffolding.dto.request.RequestCompanyDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.core.ICompanyEndPoint;
import com.wissensalt.scaffolding.service.core.ICompanyService;
import com.wissensalt.shared.dto.response.security.ResponseCompanyDTO;
import com.wissensalt.shared.entity.mapper.security.CompanyMapper;
import com.wissensalt.shared.entity.security.SecurityCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class CompanyEndPointImpl extends AScaffoldingEndPoint<SecurityCompany,RequestCompanyDTO, ResponseCompanyDTO> implements ICompanyEndPoint{

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = companyService;
        dataMapperIntegration = companyMapper;
    }
}
