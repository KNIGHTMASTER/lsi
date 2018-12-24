package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.scaffolding.dto.request.RequestCountryDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.address.ICountryEndPoint;
import com.wissensalt.scaffolding.service.address.ICountryService;
import com.wissensalt.shared.dto.response.master.ResponseCountryDTO;
import com.wissensalt.shared.entity.mapper.master.CountryMapper;
import com.wissensalt.shared.entity.master.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class CountryEndPointImpl extends AScaffoldingEndPoint<Country, RequestCountryDTO, ResponseCountryDTO> implements ICountryEndPoint {

    @Autowired
    private ICountryService countryService;

    @Autowired
    private CountryMapper countryMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = countryService;
        dataMapperIntegration = countryMapper;
    }
}
