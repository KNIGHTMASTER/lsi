package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestCityDTO;
import com.wissensalt.scaffolding.dto.request.RequestInsertCityAndProvinceIdDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.base.BaseResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseCityDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant.SecuredPath.CityPathDetails.*;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "City End Point", value = "API Scaffolding City", description = "API Scaffolding for City")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.CITY)
public interface ICityEndPoint extends IScaffoldingEndPoint<RequestCityDTO, ResponseCityDTO> {

    @ResponseBody
    @GetMapping(value = FIND_CITY_BY_PROVINCE_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find City By Province Id", notes = "Find City By Province Id", response = GenericListResponseDTO.class)
    GenericListResponseDTO findCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceId);

    @ResponseBody
    @GetMapping(value = FIND_CITY_BY_PROVINCE_CODE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find City By Province Code", notes = "Find City By Province Code", response = GenericListResponseDTO.class)
    GenericListResponseDTO findCityByProvinceCode(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceCode);

    @ResponseBody
    @GetMapping(value = FIND_CITY_BY_PROVINCE_NAME, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find City By Province Name", notes = "Find City By Province Name", response = GenericListResponseDTO.class)
    GenericListResponseDTO findCityByProvinceName(HttpServletRequest httpServletRequest, @RequestBody String p_ProvinceName);

    @ResponseBody
    @PostMapping(value = INSERT_CITY_AND_PROVINCE_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert City And Province Id", notes = "Insert City Province Id", response = GenericListResponseDTO.class)
    ResponseData insertCityAndProvinceId(HttpServletRequest httpServletRequest, @RequestBody RequestInsertCityAndProvinceIdDTO p_RequestInsertCityAndProvinceId);

    @ResponseBody
    @PostMapping(value = LOV_CITY_BY_PROVINCE_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lov City By Province Id", notes = "Lov City By Province Id", response = GenericListResponseDTO.class)
    GenericListResponseDTO lovCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody Long p_ProvinceId);

    @ResponseBody
    @PostMapping(value = FIND_PROVINCE_BY_CITY_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find Province By City Id", notes = "Province By City Id", response = GenericListResponseDTO.class)
    BaseResponseDTO findProvinceByCityId(HttpServletRequest httpServletRequest, @RequestBody Long p_CityId);
}
