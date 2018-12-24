package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.dto.request.RequestCityDTO;
import com.wissensalt.scaffolding.dto.request.RequestInsertCityAndProvinceIdDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.address.ICityEndPoint;
import com.wissensalt.scaffolding.service.address.ICityService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseCityDTO;
import com.wissensalt.shared.entity.mapper.master.CityMapper;
import com.wissensalt.shared.entity.master.City;
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
public class CityEndPointImpl extends AScaffoldingEndPoint<City, RequestCityDTO, ResponseCityDTO> implements ICityEndPoint {

    @Autowired
    private ICityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = cityService;
        dataMapperIntegration = cityMapper;
    }

    @Override
    public GenericListResponseDTO findCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceId) {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg()));
        /*try {
            result = scaffoldingResponseConstructor.constructDTOListFindResponse(dataMapperIntegration.mapEntitiesIntoDTOs(cityService.findCityByProvinceCode(p_ProvinceCode)));
        } catch (EndPointException | ServiceException e) {
            LOGGER.error(e.toString());
            result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }*/
        return result;
    }

    @RequestLogger(name = "City|Lov City By Province Id")
    @Override
    public GenericListResponseDTO lovCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody Long p_ProvinceId) {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            try {
                result = scaffoldingResponseBuilder.buildListDTOResponse(cityService.selectLOVByProvinceId(p_ProvinceId));
            } catch (ServiceException e) {
                LOGGER.error("Error Searching LOV Data : {}", e.toString());
            }
        } catch (EndPointException e) {
            LOGGER.error("Error Searching LOV Data : {}", e.toString());
        }
        return result;
    }

    @Override
    public BaseResponseDTO findProvinceByCityId(HttpServletRequest httpServletRequest, @RequestBody Long p_CityId) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        try {
            responseDTO = cityService.findProvinceByCityId(p_CityId);
        } catch (ServiceException e) {
            LOGGER.error("Province Not Found");
        }
        return responseDTO;
    }

    @Override
    public GenericListResponseDTO findCityByProvinceCode(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceCode) {
        return null;
    }

    @Override
    public GenericListResponseDTO findCityByProvinceName(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceName) {
        return null;
    }

    @Override
    public ResponseData insertCityAndProvinceId(HttpServletRequest httpServletRequest, @RequestBody RequestInsertCityAndProvinceIdDTO p_RequestInsertCityAndProvinceId) {
        return null;
    }
}
