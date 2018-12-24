package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.dto.request.RequestDistrictDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.address.IDistrictEndPoint;
import com.wissensalt.scaffolding.service.address.IDistrictService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseDistrictDTO;
import com.wissensalt.shared.entity.mapper.master.DistrictMapper;
import com.wissensalt.shared.entity.master.District;
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
public class DistrictEndPointImpl extends AScaffoldingEndPoint<District, RequestDistrictDTO, ResponseDistrictDTO> implements IDistrictEndPoint {

    @Autowired
    private IDistrictService districtService;

    @Autowired
    private DistrictMapper districtMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = districtService;
        dataMapperIntegration = districtMapper;
    }

    @RequestLogger(name = "District|Lov District By City Id")
    @Override
    public GenericListResponseDTO lovDistrictByCityId(HttpServletRequest httpServletRequest, @RequestBody Long p_CityId) {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            try {
                result = scaffoldingResponseBuilder.buildListDTOResponse(districtService.selectLOVByCityId(p_CityId));
            } catch (ServiceException e) {
                LOGGER.error("Error Searching LOV Data : {}", e.toString());
            }
        } catch (EndPointException e) {
            LOGGER.error("Error Searching LOV Data : {}", e.toString());
        }
        return result;
    }
}
