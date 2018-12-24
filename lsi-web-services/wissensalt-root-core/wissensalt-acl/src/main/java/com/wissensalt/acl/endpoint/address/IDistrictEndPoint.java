package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestDistrictDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseDistrictDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "District End Point", value = "Api Scaffolding District", description = "Api Scaffolding for Entity District")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.DISTRICT)
public interface IDistrictEndPoint extends IScaffoldingEndPoint<RequestDistrictDTO, ResponseDistrictDTO> {

    @ResponseBody
    @PostMapping(value = ScaffoldingSecurityConstant.SecuredPath.LOV_DISTRICT_BY_CITY_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lov District By City Id", notes = "Lov District By City Id", response = GenericListResponseDTO.class)
    GenericListResponseDTO lovDistrictByCityId(HttpServletRequest httpServletRequest, @RequestBody Long p_CityId);

}
