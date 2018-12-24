package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.dto.request.RequestProvinceDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.address.IProvinceEndPoint;
import com.wissensalt.scaffolding.service.address.IProvinceService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import com.wissensalt.shared.entity.mapper.master.ProvinceMapper;
import com.wissensalt.shared.entity.master.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class ProvinceEndPointImpl extends AScaffoldingEndPoint<Province, RequestProvinceDTO, ResponseProvinceDTO> implements IProvinceEndPoint {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ProvinceMapper provinceMapper;
    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = provinceService;
        dataMapperIntegration = provinceMapper;
    }

    @RequestLogger(name = "Province|lov-city-by-province-id")
    @Override
    public GenericListResponseDTO lovCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody Long p_CountryId) throws EndPointException {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            result = scaffoldingResponseBuilder.buildListResponse(provinceService.selectLOVByCountryId(p_CountryId));
        } catch (ServiceException e) {
            LOGGER.error("Error Select LOV by Country ID {}" ,e.toString());
        }
        return result;
    }
}
